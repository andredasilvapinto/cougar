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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Post_RequestTypes_List_JSON_NullEntry.xls;
package com.betfair.cougar.tests.updatedcomponenttests.standardtesting.rest;

import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum;
import com.betfair.testing.utils.cougar.manager.CougarManager;
import com.betfair.testing.utils.cougar.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that Cougar can handle a list with a null entry in the post body from a JSON request
 */
public class RestPostRequestTypesListJSONNullEntryTest {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        CougarManager cougarManager1 = CougarManager.getInstance();
        HttpCallBean getNewHttpCallBean1 = cougarManager1.getNewHttpCallBean("87.248.113.14");
        cougarManager1 = cougarManager1;
        
        getNewHttpCallBean1.setOperationName("testLargePostQA");
        
        getNewHttpCallBean1.setServiceName("baseline", "cougarBaseline");
        
        getNewHttpCallBean1.setVersion("v2");
        // Set the post body to contain a list with a null entry
        Map map2 = new HashMap();
        map2.put("RESTJSON","{\"largeRequest\":\n     {\"oddOrEven\":\"ODD\",\n      \"returnList\":true,\n      \"objects\":[\n          {\"name\":\"n1\",\"value1\":11,\"value2\":21},         \n          null,\n          {\"name\":\"n3\",\"value1\":13,\"value2\":23}],\n      \"size\":1}}\n");
        getNewHttpCallBean1.setPostQueryObjects(map2);
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp7 = new Timestamp(System.currentTimeMillis());
        // Make the JSON call to the operation
        cougarManager1.makeRestCougarHTTPCall(getNewHttpCallBean1, com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum.RESTJSON, com.betfair.testing.utils.cougar.enums.CougarMessageContentTypeEnum.JSON);
        // Create the expected response as an XML document
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument9 = xMLHelpers4.getXMLObjectFromString("<SimpleResponse><message>Names: n1nulln3 - Value1s: 11null13 - Value2s: 21null23</message></SimpleResponse>");
        // Convert the expected response to REST types for comparison with actual responses
        Map<CougarMessageProtocolRequestTypeEnum, Object> convertResponseToRestTypes10 = cougarManager1.convertResponseToRestTypes(createAsDocument9, getNewHttpCallBean1);
        // Check the JSON response is as expected
        HttpResponseBean response5 = getNewHttpCallBean1.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes10.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), response5.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response5.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response5.getHttpStatusText());
        // Check the log entries are as expected
        
        cougarManager1.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp7, new RequestLogRequirement("2.8", "testLargePostQA") );
    }

}
