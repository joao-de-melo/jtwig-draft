package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.lyncode.reflection.input.InputParameterResolver;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import com.lyncode.reflection.util.Lists2;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.functions.resolver.position.PositionParameterResolver;

import java.util.List;

public class PositionInputParameterResolver implements AnnotatedInputParameterResolver<FunctionArgument> {
    private final PositionParameterResolver positionParameterResolver;

    public PositionInputParameterResolver(PositionParameterResolver positionParameterResolver) {
        this.positionParameterResolver = positionParameterResolver;
    }

    @Override
    public Optional<FunctionArgument> resolve(Parameter parameter, JavaMethodArgument javaMethodArgument, InputParameterResolverContext<FunctionArgument> context) {
        List<JavaMethodArgument> arguments = onlyArguments(javaMethodArgument);
        int position = position(javaMethodArgument, arguments);
        return positionParameterResolver.resolve(javaMethodArgument, position, context);
    }

    private int position(final JavaMethodArgument javaMethodArgument, List<JavaMethodArgument> arguments) {
        return Lists2.positionOf(arguments, new Predicate<JavaMethodArgument>() {
            @Override
            public boolean apply(JavaMethodArgument input) {
                return input == javaMethodArgument;
            }
        });
    }

    private List<JavaMethodArgument> onlyArguments(JavaMethodArgument javaMethodArgument) {
        List<JavaMethodArgument> arguments = javaMethodArgument.method().arguments();
        return Lists2.filter(arguments, new Predicate<JavaMethodArgument>() {
            @Override
            public boolean apply(JavaMethodArgument input) {
                return input.annotation(Parameter.class).isPresent();
            }
        });
    }
}
