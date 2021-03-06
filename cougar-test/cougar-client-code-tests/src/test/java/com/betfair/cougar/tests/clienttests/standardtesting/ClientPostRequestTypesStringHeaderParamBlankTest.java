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

// Originally from ClientTests/Transport/StandardTesting/Client_Rescript_Post_RequestTypes_String_HeaderParam_Blank.xls;
package com.betfair.cougar.tests.clienttests.standardtesting;

import com.betfair.baseline.v2.BaselineSyncClient;
import com.betfair.baseline.v2.to.MandatoryParamsOperationResponseObject;
import com.betfair.baseline.v2.to.MandatoryParamsRequest;
import com.betfair.cougar.api.ExecutionContext;
import com.betfair.cougar.tests.clienttests.ClientTestsHelper;
import com.betfair.cougar.tests.clienttests.CougarClientWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Ensure that when an operation with a mandatory header param is performed against cougar via a cougar client with a blank header param, the request is sent and the response is handled correctly
 */
public class ClientPostRequestTypesStringHeaderParamBlankTest {
    @Test(dataProvider = "TransportType")
    public void doTest(CougarClientWrapper.TransportType tt) throws Exception {
        // Set up the client to use rescript transport
        CougarClientWrapper cougarClientWrapper1 = CougarClientWrapper.getInstance(tt);
        CougarClientWrapper wrapper = cougarClientWrapper1;
        BaselineSyncClient client = cougarClientWrapper1.getClient();
        ExecutionContext context = cougarClientWrapper1.getCtx();
        // Create body parameter to be passed
        MandatoryParamsRequest mandatoryParamsRequest2 = new MandatoryParamsRequest();
        mandatoryParamsRequest2.setBodyParameter1("gfdgdgfdg");
        MandatoryParamsRequest bodyParam = mandatoryParamsRequest2;
        // Make call to the method via client (with blank header parameter) and validate the response is as expected
        MandatoryParamsOperationResponseObject response3 = client.mandatoryParamsOperation(context, "", "queryParamString", bodyParam);
        assertEquals("queryParamString", response3.getQueryParameter());
        assertEquals("", response3.getHeaderParameter());
        assertEquals("gfdgdgfdg", response3.getBodyParameter1());
    }

    @DataProvider(name="TransportType")
    public Object[][] clients() {
        return ClientTestsHelper.clientsToTest();
    }

}
