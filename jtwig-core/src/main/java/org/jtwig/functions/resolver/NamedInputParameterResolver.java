package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;

public class NamedInputParameterResolver implements AnnotatedInputParameterResolver<FunctionArgument> {
    @Override
    public Optional<FunctionArgument> resolve(Parameter parameter, JavaMethodArgument javaMethodArgument, InputParameterResolverContext<FunctionArgument> context) {
        String name = parameter.value();
        for (int i = 0; i < context.size(); i++) {
            FunctionArgument functionArgument = context.value(i);
            if (!context.isUsed(i) && functionArgument.getName().isPresent()) {
                if (functionArgument.getName().get().equals(name)) {
                    context.markAsUsed(i);
                    return Optional.of(functionArgument);
                }
            }
        }

        return Optional.absent();
    }
}
