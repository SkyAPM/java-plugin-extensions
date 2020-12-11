package io.skywalking.apm.plugin.jdbc.oracle.define;

import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.plugin.jdbc.PSSetterDefinitionOfJDBCInstrumentation;

/**
 * @author ray_bi
 * @since 2020-12-11
 */
public class OraclePreparedStatementIgnoredSetterInstrumentation
    extends OraclePrepareStatementInstrumentation {
  
  @Override
    public final InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new PSSetterDefinitionOfJDBCInstrumentation(true)
        };
    }
    
}
