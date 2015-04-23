package org.jtwig.functions.resolver;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.annotations.Parameter;

public interface AnnotatedInputParameterResolver<T> {
    Optional<FunctionArgument> resolve(Parameter parameter, JavaMethodArgument javaMethodArgument, InputParameterResolverContext<FunctionArgument> context);
}
