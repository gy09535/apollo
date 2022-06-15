package com.ctrip.framework.apollo.spring.cloud.listener;

import com.ctrip.framework.apollo.spring.events.ApolloConfigChangeEvent;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

public class SpringCloudConfigChangeListener implements ApplicationListener<ApolloConfigChangeEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private RefreshScope refreshScope;

    @Override
    public void onApplicationEvent(ApolloConfigChangeEvent event) {
        if (refreshScope != null) {
            refreshScope.refreshAll();
        }

        if (event.getConfigChangeEvent() != null && !event.getConfigChangeEvent().changedKeys().isEmpty()) {
            applicationContext.publishEvent(new EnvironmentChangeEvent(event.getConfigChangeEvent().changedKeys()));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        refreshScope = applicationContext.getBean(RefreshScope.class);
    }
}
