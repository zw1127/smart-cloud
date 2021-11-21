package org.smartframework.cloud.starter.mybatis.plus.autoconfigure;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.smartframework.cloud.starter.configure.constants.OrderConstant;
import org.smartframework.cloud.starter.mybatis.plus.injector.SmartSqlInjector;
import org.smartframework.cloud.starter.mybatis.plus.plugin.MybatisSqlLogInterceptor;
import org.smartframework.cloud.utility.spring.condition.ConditionEnableLogInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * mybatis plus配置
 *
 * @author liyulin
 * @date 2020-09-28
 */
@Configuration
public class MyBatisPlusAutoConfiguration {

    @Bean
    @Order(OrderConstant.MYBATIS_SQL_LOG_INTERCEPTOR)
    @Conditional(ConditionEnableLogInfo.class)
    @ConditionalOnProperty(prefix = "smart.cloud.mybatis.log", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MybatisSqlLogInterceptor mybatisSqlLogInterceptor() {
        return new MybatisSqlLogInterceptor();
    }

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    @Order(OrderConstant.MYBATIS_PLUS_INTERCEPTOR)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    public SmartSqlInjector smartSqlInjector() {
        return new SmartSqlInjector();
    }

}