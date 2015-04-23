package org.jtwig.configuration;

import com.google.common.base.Function;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.PropertyResolver;
import org.jtwig.resource.ResourceResolver;

public interface Configuration {
    JtwigParser parser();
    ResourceResolver resourceResolver();
    FunctionResolver functionResolver();
    PropertyResolver propertyResolver();
    Function<Object,String> jsonMapper();

    <T> T parameter(String name, T defaultValue);
    <T> T parameter(ConfigurationParameter<T> configurationParameter);

}
