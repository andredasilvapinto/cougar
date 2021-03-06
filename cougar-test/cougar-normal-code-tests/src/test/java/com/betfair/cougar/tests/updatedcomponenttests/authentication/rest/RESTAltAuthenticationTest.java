/*
 * Copyright 2013, The Sporting Exchange Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Originally from UpdatedComponentTests/Authentication/REST/REST_Alt_Authentication.xls;
package com.betfair.cougar.tests.updatedcomponenttests.authentication.rest;

import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum;
import com.betfair.testing.utils.cougar.manager.AccessLogRequirement;
import com.betfair.testing.utils.cougar.manager.CougarManager;
import com.betfair.testing.utils.cougar.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Verify that when the auth credentials are provided for the alternative endpoint (www), Cougar correctly reads headers X-AltToken-Username & X-AtToken-Password (and does not rewrite them)
 */
public class RESTAltAuthenticationTest {
    @Test
    public void doTest() throws Exception {

        CougarManager cougarManager1 = CougarManager.getInstance();
        HttpCallBean getNewHttpCallBean1 = cougarManager1.getNewHttpCallBean("87.248.113.14");
        cougarManager1 = cougarManager1;

        getNewHttpCallBean1.setAlternativeURL("/www");


        getNewHttpCallBean1.setOperationName("testIdentityChain", "identityChain");

        getNewHttpCallBean1.setServiceName("baseline", "cougarBaseline");

        getNewHttpCallBean1.setVersion("v2");

        Map map2 = new HashMap();
        map2.put("Username","foo");
        map2.put("Password","bar");
        getNewHttpCallBean1.setAuthCredentials(map2);


        Timestamp getTimeAsTimeStamp8 = new Timestamp(System.currentTimeMillis());

        cougarManager1.makeRestCougarHTTPCalls(getNewHttpCallBean1);

        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers4.getXMLObjectFromString("<IdentChain><identities><Ident><credentialName>CREDENTIAL: Username</credentialName><credentialValue>foo</credentialValue><principal>PRINCIPAL: Username</principal></Ident><Ident><credentialName>CREDENTIAL: Password</credentialName><credentialValue>bar</credentialValue><principal>PRINCIPAL: Password</principal></Ident></identities></IdentChain>");

        Map<CougarMessageProtocolRequestTypeEnum, Object> convertResponseToRestTypes11 = cougarManager1.convertResponseToRestTypes(createAsDocument10, getNewHttpCallBean1);

        HttpResponseBean getResponseObjectsByEnum12 = getNewHttpCallBean1.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes11.get(CougarMessageProtocolRequestTypeEnum.RESTXML), getResponseObjectsByEnum12.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, getResponseObjectsByEnum12.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", getResponseObjectsByEnum12.getHttpStatusText());

        Map<String, String> headerMap = getResponseObjectsByEnum12.getFlattenedResponseHeaders();

        boolean Response = getResponseObjectsByEnum12.isKeyInTheHeader("X-AltToken-Username");
        AssertionUtils.multiAssertEquals(false, Response);

        HttpResponseBean getResponseObjectsByEnum14 = getNewHttpCallBean1.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes11.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), getResponseObjectsByEnum14.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, getResponseObjectsByEnum14.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", getResponseObjectsByEnum14.getHttpStatusText());

        headerMap = getResponseObjectsByEnum14.getFlattenedResponseHeaders();

        Response = getResponseObjectsByEnum14.isKeyInTheHeader("X-AltToken-Username");
        AssertionUtils.multiAssertEquals(false, Response);

        HttpResponseBean getResponseObjectsByEnum16 = getNewHttpCallBean1.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes11.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), getResponseObjectsByEnum16.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, getResponseObjectsByEnum16.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", getResponseObjectsByEnum16.getHttpStatusText());

        headerMap = getResponseObjectsByEnum16.getFlattenedResponseHeaders();

        Response = getResponseObjectsByEnum16.isKeyInTheHeader("X-AltToken-Username");
        AssertionUtils.multiAssertEquals(false, Response);

        HttpResponseBean getResponseObjectsByEnum18 = getNewHttpCallBean1.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes11.get(CougarMessageProtocolRequestTypeEnum.RESTXML), getResponseObjectsByEnum18.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, getResponseObjectsByEnum18.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", getResponseObjectsByEnum18.getHttpStatusText());

        headerMap = getResponseObjectsByEnum18.getFlattenedResponseHeaders();

        Response = getResponseObjectsByEnum18.isKeyInTheHeader("X-AltToken-Username");
        AssertionUtils.multiAssertEquals(false, Response);


        cougarManager1.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp8, new RequestLogRequirement("2.8", "testIdentityChain"),new RequestLogRequirement("2.8", "testIdentityChain"),new RequestLogRequirement("2.8", "testIdentityChain"),new RequestLogRequirement("2.8", "testIdentityChain") );

        cougarManager1.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp8, new AccessLogRequirement(null, null, "Ok"),new AccessLogRequirement(null, null, "Ok"),new AccessLogRequirement(null, null, "Ok"),new AccessLogRequirement(null, null, "Ok") );
    }

}
