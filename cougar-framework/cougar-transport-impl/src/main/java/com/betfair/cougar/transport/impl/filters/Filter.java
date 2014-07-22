package com.betfair.cougar.transport.impl.filters;

import com.betfair.cougar.api.ExecutionContextWithTokens;
import com.betfair.cougar.transport.api.TransportCommand;

public interface Filter<T extends TransportCommand> {

    // returns whether the execution should continue (true) or stop (false)
    boolean before(ExecutionContextWithTokens context, T command);

    // TODO: maybe add array of ExecutionResult
    void after(ExecutionContextWithTokens context, T command);
}
