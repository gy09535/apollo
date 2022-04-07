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
package com.ctrip.framework.apollo.spring.property;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class SpringCloudAutoUpdateConfigChangeListener extends AutoUpdateConfigChangeListener {
  final ApplicationContext applicationContext;

  public SpringCloudAutoUpdateConfigChangeListener(
      Environment environment,
      ConfigurableListableBeanFactory beanFactory,
      ApplicationContext applicationContext) {
    super(environment, beanFactory);
    this.applicationContext = applicationContext;
  }

  @Override
  public void onChange(ConfigChangeEvent changeEvent) {
    super.onChange(changeEvent);
    adapterSpringCloud(changeEvent);
  }

  private void adapterSpringCloud(ConfigChangeEvent changeEvent) {
    RefreshScope refreshScope = applicationContext.getBean(RefreshScope.class);
    if (refreshScope != null) {
      refreshScope.refreshAll();
    }
    applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
  }
}
