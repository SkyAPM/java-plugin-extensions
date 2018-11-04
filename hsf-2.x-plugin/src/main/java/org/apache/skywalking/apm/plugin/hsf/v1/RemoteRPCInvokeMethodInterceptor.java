package org.apache.skywalking.apm.plugin.hsf.v1;

import com.taobao.hsf.invocation.Invocation;
import com.taobao.hsf.protocol.ServiceURL;
import java.lang.reflect.Method;
import org.apache.skywalking.apm.agent.core.context.CarrierItem;
import org.apache.skywalking.apm.agent.core.context.ContextCarrier;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;

public class RemoteRPCInvokeMethodInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
        MethodInterceptResult result) throws Throwable {
        Invocation invocation = (Invocation)allArguments[0];
        ServiceURL serviceURL = invocation.getTargetAddress();
        ContextCarrier contextCarrier = new ContextCarrier();
        AbstractSpan activeSpan = ContextManager.createExitSpan(invocation.getTargetServiceUniqueName(), contextCarrier, serviceURL.getHost() + serviceURL.getPort());

        CarrierItem next = contextCarrier.items();
        while (next.hasNext()) {
            next = next.next();
            invocation.setRequestProps(next.getHeadKey(), next.getHeadValue());
        }
        activeSpan.setComponent(new HSFComponent());
        Tags.URL.set(activeSpan, invocation.getTargetAddress().getUrl());
        SpanLayer.asRPCFramework(activeSpan);
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
        Object ret) throws Throwable {
        ContextManager.stopSpan();
        return ret;
    }

    @Override public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
        Class<?>[] argumentsTypes, Throwable t) {
        ContextManager.activeSpan().errorOccurred().log(t);
    }
}
