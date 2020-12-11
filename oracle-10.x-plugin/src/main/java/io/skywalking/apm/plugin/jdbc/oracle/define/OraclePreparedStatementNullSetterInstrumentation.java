package io.skywalking.apm.plugin.jdbc.oracle.define;

import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.plugin.jdbc.JDBCPreparedStatementNullSetterInstanceMethodsInterceptPoint;

/**
 * @author ray_bi
 * @since 2020-12-11
 */
public class OraclePreparedStatementNullSetterInstrumentation
    extends OraclePrepareStatementInstrumentation {

  @Override
  public final InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
    return new InstanceMethodsInterceptPoint[] {
        new JDBCPreparedStatementNullSetterInstanceMethodsInterceptPoint()};
  }
}
