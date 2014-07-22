package com.betfair.cougar.transport.impl.filters;

import com.betfair.cougar.api.ExecutionContextWithTokens;
import com.betfair.cougar.transport.api.TransportCommand;

import java.util.concurrent.atomic.AtomicLong;

public class QOSFilter implements Filter {

    public static final long MAX_REQ = 1000L;

    public AtomicLong counter = new AtomicLong(0);

    @Override
    public boolean before(ExecutionContextWithTokens context, TransportCommand command) {
        return counter.getAndIncrement() < MAX_REQ;
        // decrement
    }

    @Override
    public void after(ExecutionContextWithTokens context, TransportCommand command) {
    }
}
