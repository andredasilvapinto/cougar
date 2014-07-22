package com.betfair.cougar.transport.impl.filters;

import com.betfair.cougar.api.ExecutionContextWithTokens;
import com.betfair.cougar.transport.api.protocol.http.HttpCommand;

public class ZipkinHTTPFilter extends ZipkinAbstractFilter implements HTTPFilter {

    @Override
    public boolean before(ExecutionContextWithTokens executionContext, HttpCommand command) {
        // EMIT START
        return true;
    }

    @Override
    public void after(ExecutionContextWithTokens context, HttpCommand command) {
        // EMIT END
    }
}
