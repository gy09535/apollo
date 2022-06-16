/*
 * Copyright 2022 Apollo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.ctrip.framework.apollo.spring.cloud;

import com.ctrip.framework.apollo.spring.cloud.listener.SpringCloudEnvironmentChangeEventConfigChangeListener;
import com.ctrip.framework.apollo.spring.cloud.listener.SpringCloudRefreshScopeConfigChangeListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApolloClientSpringCloudAutoConfiguration {

    @Bean
    @ConditionalOnClass(name = {"org.springframework.cloud.context.scope.refresh.RefreshScope"})
    public SpringCloudRefreshScopeConfigChangeListener springCloudConfigPropertySourceProcessor() {
        return new SpringCloudRefreshScopeConfigChangeListener();
    }

    @Bean
    @ConditionalOnClass(name = {"org.springframework.cloud.context.environment.EnvironmentChangeEvent"})
    public SpringCloudEnvironmentChangeEventConfigChangeListener springCloudEnvironmentChangeEventConfigChangeListener(){
        return  new SpringCloudEnvironmentChangeEventConfigChangeListener();
    }
}
