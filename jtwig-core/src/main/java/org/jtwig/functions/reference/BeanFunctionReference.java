package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.lyncode.reflection.MethodInvoker;
import com.lyncode.reflection.model.bean.BeanMethod;
import org.jtwig.functions.FunctionArgument;

import java.util.Collections;
import java.util.List;

public class BeanFunctionReference implements FunctionReference {
    private final String name;
    private final BeanMethod beanMethod;
    private final Supplier<MethodInvoker<FunctionArgument>> methodInvoker;

    public BeanFunctionReference(String name, BeanMethod beanMethod, Supplier<MethodInvoker<FunctionArgument>> methodInvoker) {
        this.name = name;
        this.beanMethod = beanMethod;
        this.methodInvoker = methodInvoker;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<Supplier> calculate(List<FunctionArgument> arguments) {
        List<BeanMethod> beanMethods = Collections.singletonList(beanMethod);
        MethodInvoker.Request<FunctionArgument> request = new MethodInvoker.Request<>(beanMethods, arguments);
        return Optional.fromNullable((Supplier) methodInvoker.get().invoke(request).orNull());
    }
}
