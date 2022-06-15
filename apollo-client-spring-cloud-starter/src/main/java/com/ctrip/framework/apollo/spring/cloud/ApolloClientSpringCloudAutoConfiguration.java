package com.ctrip.framework.apollo.spring.cloud;

import com.ctrip.framework.apollo.spring.cloud.listener.SpringCloudConfigChangeListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApolloClientSpringCloudAutoConfiguration {

    @Bean
    @ConditionalOnClass(name = {"org.springframework.cloud.context.scope.refresh.RefreshScope"})
    public SpringCloudConfigChangeListener springCloudConfigPropertySourceProcessor() {
        return new SpringCloudConfigChangeListener();
    }
}
