package org.jtwig;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;

import java.util.HashMap;
import java.util.Map;

public class JtwigModel {
    private final Map<String, JtwigValue> values;

    public static JtwigModel newModel() {
        return new JtwigModel(new HashMap<String, JtwigValue>());
    }

    public JtwigModel(Map<String, JtwigValue> values) {
        this.values = values;
    }

    public JtwigModel define(String name, Object value) {
        values.put(name, new JtwigValue(value));
        return this;
    }

    public JtwigModel merge(JtwigModel model) {
        values.putAll(model.values);
        return this;
    }

    public Optional<JtwigValue> resolve(String key) {
        return Optional.fromNullable(values.get(key));
    }
}
