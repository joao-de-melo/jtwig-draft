package org.jtwig.functions;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;

public class FunctionArgument {
    private final Optional<String> name;
    private final JtwigValue value;

    public FunctionArgument(Optional<String> name, JtwigValue value) {
        this.name = name;
        this.value = value;
    }

    public Optional<String> getName() {
        return name;
    }

    public JtwigValue getValue() {
        return value;
    }
}
