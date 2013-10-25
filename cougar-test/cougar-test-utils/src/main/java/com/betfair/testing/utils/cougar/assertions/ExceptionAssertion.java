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

package com.betfair.testing.utils.cougar.assertions;


import com.betfair.testing.utils.cougar.misc.AggregatedStepExpectedOutputMetaData;
import com.betfair.testing.utils.cougar.misc.ObjectUtil;

public class ExceptionAssertion implements IAssertion {

	@Override
	public void execute(String message, Object passedExpObject, Object passedActObject, AggregatedStepExpectedOutputMetaData outputMetaData) throws AssertionError {

		Throwable expException = null;
		Throwable actException = null;

		try{
			expException = (Throwable) passedExpObject;
			actException = (Throwable) passedActObject;
			}
		catch(ClassCastException e)		{
            AssertionUtils.actionFail("Class cast exception : " + e);
			return;
		}

		//check that the actual object implements the interface of the expected

		boolean checkBehaviour = ObjectUtil.checkBehaviour(actException.getClass(), expException.getClass());

        AssertionUtils.jettAssertTrue("Check Exception behaviour : ", checkBehaviour);


	}

	@Override
	public Object preProcess(Object actualObject, AggregatedStepExpectedOutputMetaData expectedObjectMetaData)throws AssertionError {

			if ((ObjectUtil.isExceptionObject(expectedObjectMetaData
                    .getMetaDataAtIndex(0).getValueAtIndex(0).getClass())) || (ObjectUtil.isThrowableObject(expectedObjectMetaData
							.getMetaDataAtIndex(0).getValueAtIndex(0).getClass()))) {

				return expectedObjectMetaData.getMetaDataAtIndex(0).getValueAtIndex(0);

			} else {
				throw new AssertionError("Unable to build Exception Object from metadata at present");
			}
	}


}