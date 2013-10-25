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

// Originally from UpdatedComponentTests/StandardTesting/SOAP/SOAP_TestLargePost_ZEROONEMANY_Zero.xls;
package com.betfair.cougar.tests.updatedcomponenttests.standardtesting.soap;

import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.manager.CougarManager;
import com.betfair.testing.utils.cougar.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Test that when Cougar is passed a Large Request it can correctly deserialise it and count how many items are in the list (incorrect number  of items given - zero items given)
 */
public class SOAPTestLargePostZEROONEMANYZeroTest {
    // ERROR:  'org.xml.sax.SAXParseException; cvc-complex-type.2.4.b: The content of element 'bas:objects' is not complete. One of '{"http://www.betfair.com/servicetypes/v2/Baseline/":ComplexObject}' is expected.'
    @Test
    public void doTest() throws Exception {
        // Create the SOAP request as an XML Document
        XMLHelpers xMLHelpers1 = new XMLHelpers();
        Document createAsDocument1 = xMLHelpers1.getXMLObjectFromString("<TestLargePostRequest><largeRequest><size>2</size><objects><ComplexObject><value1>34</value1></ComplexObject></objects><oddOrEven>ODD</oddOrEven></largeRequest></TestLargePostRequest>");
        // Set up the Http Call Bean to make the request
        CougarManager cougarManager2 = CougarManager.getInstance();
        HttpCallBean getNewHttpCallBean2 = cougarManager2.getNewHttpCallBean("87.248.113.14");
        cougarManager2 = cougarManager2;
        
        cougarManager2.setCougarFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");
        
        getNewHttpCallBean2.setServiceName("Baseline");
        
        getNewHttpCallBean2.setVersion("v2");
        // Set the created SOAP request as the PostObject
        getNewHttpCallBean2.setPostObjectForRequestType(createAsDocument1, "SOAP");
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp8 = new Timestamp(System.currentTimeMillis());
        // Make the SOAP call to the operation
        cougarManager2.makeSoapCougarHTTPCalls(getNewHttpCallBean2);
        // Create the expected response object as an XML document (A message stating if the number of items in the list was correct)
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers4.getXMLObjectFromString("<response><message>There were 2 items specified in the list, 1 actually</message></response>");
        // Convert the expected response to SOAP for comparison with actual response
        Map<String, Object> convertResponseToSOAP11 = cougarManager2.convertResponseToSOAP(createAsDocument10, getNewHttpCallBean2);
        // Check the response is as expected
        HttpResponseBean response5 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.SOAP);
        AssertionUtils.multiAssertEquals(convertResponseToSOAP11.get("SOAP"), response5.getResponseObject());
        
        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected
        
        cougarManager2.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp8, new RequestLogRequirement("2.8", "testLargePost") );
    }

}
