package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;

public class AnnotatedParameterResolver implements InputParameterResolver<FunctionArgument> {
    private final AnnotatedInputParameterResolver<FunctionArgument> delegate;

    public AnnotatedParameterResolver(AnnotatedInputParameterResolver<FunctionArgument> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<FunctionArgument> resolve(JavaMethodArgument argument, InputParameterResolverContext<FunctionArgument> context) {
        Optional<Parameter> annotation = argument.annotation(Parameter.class);
        if (annotation.isPresent()) {
            return delegate.resolve(annotation.get(), argument, context);
        }
        return Optional.absent();
    }
}
