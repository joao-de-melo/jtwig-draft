package org.jtwig.context.values;

import com.google.common.base.Optional;

import org.jtwig.util.JtwigValue;

import java.util.Map;

public class SimpleValueContext implements ValueContext {
    private final Map<String, JtwigValue> source;

    public SimpleValueContext(Map<String, JtwigValue> source) {
        this.source = source;
    }

    @Override
    public Optional<JtwigValue> value(String key) {
        return Optional.fromNullable(source.get(key));
    }

    @Override
    public SimpleValueContext add(String key, Object value) {
        source.put(key, new JtwigValue(value));
        return this;
    }
}
