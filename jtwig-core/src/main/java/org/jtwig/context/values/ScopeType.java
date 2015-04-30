package org.jtwig.context.values;

import org.jtwig.util.JtwigValue;

import java.util.HashMap;

public enum ScopeType {
    SHARE(new ValueContextFactory() {
        @Override
        public ValueContext create(ValueContext previous) {
            return previous;
        }
    }),
    SHARE_EDIT_OLD(new ValueContextFactory() {
        @Override
        public ValueContext create(ValueContext previous) {
            return new NewlyScopedValueContext(previous, new SimpleValueContext(new HashMap<String, JtwigValue>()));
        }
    });

    private final ValueContextFactory factory;

    ScopeType(ValueContextFactory factory) {
        this.factory = factory;
    }

    public ValueContextFactory getFactory() {
        return factory;
    }
}
