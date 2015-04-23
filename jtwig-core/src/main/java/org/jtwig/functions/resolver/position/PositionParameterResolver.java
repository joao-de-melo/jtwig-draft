package org.jtwig.functions.resolver.position;

import com.google.common.base.Optional;
import com.lyncode.reflection.input.InputParameterResolverContext;
import com.lyncode.reflection.model.java.JavaMethodArgument;
import org.jtwig.functions.FunctionArgument;

public interface PositionParameterResolver {
    Optional<FunctionArgument> resolve(JavaMethodArgument javaMethodArgument, int position, InputParameterResolverContext<FunctionArgument> context);
}
