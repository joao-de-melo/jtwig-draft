package org.jtwig.functions.extract;

import com.lyncode.reflection.model.bean.BeanMethod;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.annotations.JtwigFunction;

public class FunctionNameExtractor {
    public String extractName (BeanMethod beanMethod) {
        JtwigFunction jtwigFunction = beanMethod.method().annotation(JtwigFunction.class).get();
        if (StringUtils.isBlank(jtwigFunction.value())) {
            return beanMethod.method().name();
        } else {
            return jtwigFunction.value();
        }
    }
}
