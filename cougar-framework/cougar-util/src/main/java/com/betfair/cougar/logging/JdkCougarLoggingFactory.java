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

package com.betfair.cougar.logging;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * JDK logger implementation of @see CougarLoggingFactory interface
 */
public class JdkCougarLoggingFactory implements CougarLoggingFactory {

    private static final ConcurrentMap<String, CougarLogger> LOGGERS = new ConcurrentHashMap<String, CougarLogger>();

    static CougarLogger getLog(String loggerName) {
        CougarLogger result = LOGGERS.get(loggerName);
        if (result == null) {
            result = new CougarLoggerImpl(loggerName);
            LOGGERS.put(loggerName, result);
        }
        return result;
    }

    @Override
    public CougarLogger getLogger(String loggerName) {
        return getLog(loggerName);
    }

    @Override
    public void suppressAllRootLoggerOutput() {
        getLog("").removeHandlers();
    }

}
