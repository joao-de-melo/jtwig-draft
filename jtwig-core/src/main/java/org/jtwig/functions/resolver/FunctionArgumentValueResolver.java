package org.jtwig.functions.resolver;

import com.lyncode.reflection.input.InputParameterValueResolver;
import org.jtwig.functions.FunctionArgument;

public class FunctionArgumentValueResolver implements InputParameterValueResolver<FunctionArgument> {
    @Override
    public Object resolve(FunctionArgument functionArgument) {
        return functionArgument.getValue().asObject();
    }
}
