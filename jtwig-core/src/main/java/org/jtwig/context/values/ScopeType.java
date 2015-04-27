package org.jtwig.context.values;

import org.jtwig.util.JtwigValue;

import java.util.HashMap;

public enum ScopeType {
    CLEAN(new ValueContextFactory() {
        @Override
        public ValueContext create(ValueContext previous) {
            return new SimpleValueContext(new HashMap<String, JtwigValue>());
        }
    }),
    SHARE(new ValueContextFactory() {
        @Override
        public ValueContext create(ValueContext previous) {
            return previous;
        }
    }),
    SHARE_VIEW(new ValueContextFactory() {
        @Override
        public ValueContext create(ValueContext previous) {
            return new ScopedValueContext(previous, new SimpleValueContext(new HashMap<String, JtwigValue>()));
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
