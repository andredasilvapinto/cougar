package com.betfair.cougar.transport.impl.filters;

import com.betfair.cougar.api.ExecutionContextWithTokens;
import com.betfair.cougar.transport.api.TransportCommand;

import java.util.Collections;
import java.util.List;

public class FiltersDelegate {

    private List<Filter<TransportCommand>> filters = Collections.emptyList();

    public FiltersDelegate(List<Filter<TransportCommand>> filters) {
        this.filters = filters;
    }

    public <T extends TransportCommand> boolean applyBeforeFilters(ExecutionContextWithTokens executionContext, T command) {
        for (Filter<TransportCommand> filter : filters) {
            if (!filter.before(executionContext, command)) {
                // stopped execution (e.g. QOS)
                return false;
            }
        }

        return true;
    }

    public <T extends TransportCommand> void applyAfterFilters(ExecutionContextWithTokens executionContext, T command) {
        for (Filter<TransportCommand> filter : filters) {
            filter.after(executionContext, command);
        }
    }
}
