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
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SelfDefineDiscoveryServiceTest {

    private SelfDefineDiscoveryService selfDefineDiscoveryService;


    @Test
    public void testGetServiceInstancesWithInvalidServiceId() {
        String someInvalidServiceId = "someInvalidServiceId";
        SelfDefineDiscoveryService.SelfDefineConfig selfDefineConfig = new SelfDefineDiscoveryService.SelfDefineConfig();
        selfDefineConfig.setApolloConfigServiceUrl("");
        selfDefineConfig.setApolloAdminServiceUrl("");
        selfDefineDiscoveryService = new SelfDefineDiscoveryService(selfDefineConfig);
        assertTrue(selfDefineDiscoveryService.getServiceInstances(someInvalidServiceId).isEmpty());
    }


    @Test
    public void testGetConfigServiceInstances() {
        SelfDefineDiscoveryService.SelfDefineConfig selfDefineConfig = new SelfDefineDiscoveryService.SelfDefineConfig();
        selfDefineConfig.setApolloAdminServiceUrl("");
        String someUrl = "http://some-host/some-path";
        selfDefineConfig.setApolloConfigServiceUrl(someUrl);
        selfDefineDiscoveryService = new SelfDefineDiscoveryService(selfDefineConfig);
        List<ServiceDTO> serviceDTOList = selfDefineDiscoveryService
                .getServiceInstances(ServiceNameConsts.APOLLO_CONFIGSERVICE);

        assertEquals(1, serviceDTOList.size());
        ServiceDTO serviceDTO = serviceDTOList.get(0);

        assertEquals(ServiceNameConsts.APOLLO_CONFIGSERVICE, serviceDTO.getAppName());
        assertEquals(String.format("%s:%s", ServiceNameConsts.APOLLO_CONFIGSERVICE, someUrl),
                serviceDTO.getInstanceId());
        assertEquals(someUrl, serviceDTO.getHomepageUrl());
    }

    @Test
    public void testGetAdminServiceInstances() {
        SelfDefineDiscoveryService.SelfDefineConfig selfDefineConfig = new SelfDefineDiscoveryService.SelfDefineConfig();
        selfDefineConfig.setApolloConfigServiceUrl("");
        String someUrl = "http://some-host/some-path";
        selfDefineConfig.setApolloAdminServiceUrl(someUrl);
        selfDefineDiscoveryService = new SelfDefineDiscoveryService(selfDefineConfig);
        List<ServiceDTO> serviceDTOList = selfDefineDiscoveryService
                .getServiceInstances(ServiceNameConsts.APOLLO_ADMINSERVICE);

        ServiceDTO serviceDTO = serviceDTOList.get(0);

        assertEquals(ServiceNameConsts.APOLLO_ADMINSERVICE, serviceDTO.getAppName());
        assertEquals(String.format("%s:%s", ServiceNameConsts.APOLLO_ADMINSERVICE, someUrl),
                serviceDTO.getInstanceId());
        assertEquals(someUrl, serviceDTO.getHomepageUrl());
    }
}
