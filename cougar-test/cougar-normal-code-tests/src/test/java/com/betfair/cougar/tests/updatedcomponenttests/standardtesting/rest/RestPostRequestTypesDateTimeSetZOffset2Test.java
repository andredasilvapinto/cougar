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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Post_RequestTypes_DateTimeSet_ZOffset_2.xls;
package com.betfair.cougar.tests.updatedcomponenttests.standardtesting.rest;

import com.betfair.testing.utils.cougar.misc.TimingHelpers;
import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.enums.CougarMessageProtocolRequestTypeEnum;
import com.betfair.testing.utils.cougar.manager.CougarManager;
import com.betfair.testing.utils.cougar.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Ensure that Cougar can handle the dateTimeSet data type in the post body with a date specifying UTC timezone
 */
public class RestPostRequestTypesDateTimeSetZOffset2Test {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        CougarManager cougarManager1 = CougarManager.getInstance();
        HttpCallBean hbean = cougarManager1.getNewHttpCallBean("87.248.113.14");
        CougarManager hinstance = cougarManager1;
        
        hbean.setOperationName("dateTimeSetOperation");
        
        hbean.setServiceName("baseline", "cougarBaseline");
        
        hbean.setVersion("v2");
        // Create a date time object expected to be in the response object

        String date1 = TimingHelpers.convertUTCDateTimeToCougarFormat((int) 2009, (int) 2, (int) 1, (int) 11, (int) 50, (int) 0, (int) 435);
        // Create a date time object expected to be in the response object

        String date2 = TimingHelpers.convertUTCDateTimeToCougarFormat((int) 2009, (int) 2, (int) 1, (int) 12, (int) 50, (int) 0, (int) 435);
        // Set the post body to contain a date time set object containing dates specifying the UTC timezone
        hbean.setRestPostQueryObjects(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream("<message><dateTimeSet><Date>2009-02-01T11:50:00.435Z</Date><Date>2009-02-01T12:50:00.435Z</Date></dateTimeSet></message>".getBytes())));
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp11 = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation
        hinstance.makeRestCougarHTTPCalls(hbean);
        // Create the expected response as an XML document (using the date object created earlier)
        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document expectedXML = xMLHelpers5.createAsDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(("<DateTimeSetOperationResponseObject><responseSet><Date>"+date1+"</Date><Date>"+date2+"</Date></responseSet></DateTimeSetOperationResponseObject>").getBytes())));
        // Convert the expected response to REST types for comparison with actual reponses
        Map<CougarMessageProtocolRequestTypeEnum, Object> expectedREST = hinstance.convertResponseToRestTypes(expectedXML, hbean);
        // Check the 4 responses are as expected
        HttpResponseBean response6 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(expectedREST.get(CougarMessageProtocolRequestTypeEnum.RESTXML), response6.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response6.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response6.getHttpStatusText());
        
        HttpResponseBean response7 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(expectedREST.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), response7.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response7.getHttpStatusText());
        
        HttpResponseBean response8 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(expectedREST.get(CougarMessageProtocolRequestTypeEnum.RESTJSON), response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response8.getHttpStatusText());
        
        HttpResponseBean response9 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(expectedREST.get(CougarMessageProtocolRequestTypeEnum.RESTXML), response9.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response9.getHttpStatusText());
        
        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected
        
        hinstance.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp11, new RequestLogRequirement("2.8", "dateTimeSetOperation"),new RequestLogRequirement("2.8", "dateTimeSetOperation"),new RequestLogRequirement("2.8", "dateTimeSetOperation"),new RequestLogRequirement("2.8", "dateTimeSetOperation") );
    }

}
