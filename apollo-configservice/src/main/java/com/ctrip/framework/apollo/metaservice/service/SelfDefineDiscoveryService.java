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
package com.ctrip.framework.apollo.metaservice.service;

import com.ctrip.framework.apollo.core.ServiceNameConsts;
import com.ctrip.framework.apollo.core.dto.ServiceDTO;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Profile("self-define-discovery")
public class SelfDefineDiscoveryService implements DiscoveryService{

    private final Map<String, String> SERVICE_ID_TO_CONFIG_NAME;
    private static final Splitter COMMA_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    public SelfDefineDiscoveryService(SelfDefineConfig selfDefineConfig) {
        SERVICE_ID_TO_CONFIG_NAME =
                ImmutableMap.of(
                        ServiceNameConsts.APOLLO_CONFIGSERVICE,
                        selfDefineConfig.getApolloConfigServiceUrl(),
                        ServiceNameConsts.APOLLO_ADMINSERVICE,
                        selfDefineConfig.getApolloAdminServiceUrl());
    }

    @Override
    public List<ServiceDTO> getServiceInstances(String serviceId) {
        String directUrl = SERVICE_ID_TO_CONFIG_NAME.get(serviceId);
        if (Strings.isNullOrEmpty(directUrl)) {
            return Collections.emptyList();
        }

        List<ServiceDTO> serviceDTOList = Lists.newLinkedList();
        COMMA_SPLITTER.split(directUrl).forEach(url -> {
            ServiceDTO service = new ServiceDTO();
            service.setAppName(serviceId);
            service.setInstanceId(String.format("%s:%s", serviceId, url));
            service.setHomepageUrl(url);
            serviceDTOList.add(service);
        });

        return serviceDTOList;
    }

    @Service
    @Profile("self-define-discovery")
    @ConfigurationProperties(prefix = "self-define-discovery")
    public static class SelfDefineConfig {
        public String getApolloConfigServiceUrl() {
            return apolloConfigServiceUrl;
        }

        public String getApolloAdminServiceUrl() {
            return apolloAdminServiceUrl;
        }

        public void setApolloConfigServiceUrl(String apolloConfigService) {
            this.apolloConfigServiceUrl = apolloConfigService;
        }

        public void setApolloAdminServiceUrl(String apolloAdminService) {
            this.apolloAdminServiceUrl = apolloAdminService;
        }

        private String apolloConfigServiceUrl;

        private String apolloAdminServiceUrl;
    }
}
