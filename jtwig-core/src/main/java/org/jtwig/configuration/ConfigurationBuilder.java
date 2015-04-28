package org.jtwig.configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.functions.resolver.FunctionResolverBuilder;
import org.jtwig.parser.JtwigParser;
import org.jtwig.parser.JtwigParserBuilder;
import org.jtwig.parser.config.ParserConfigurationBuilder;
import org.jtwig.property.*;
import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.resource.resolver.CompositeResourceResolver;
import org.jtwig.resource.resolver.ResourceResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationBuilder implements Builder<Configuration> {
    public static ConfigurationBuilder configuration() {
        return new ConfigurationBuilder();
    }

    private boolean defaults = true;
    private Collection<ResourceResolver> resourceResolvers = new ArrayList<ResourceResolver>();
    private Collection<PropertyResolver> propertyResolvers = new ArrayList<PropertyResolver>();
    private ParserConfigurationBuilder parserConfigurationBuilder = ParserConfigurationBuilder.parserConfiguration();
    private FunctionResolverBuilder functionResolverBuilder = FunctionResolverBuilder.newBuilder();
    private Function<Object, String> jsonMapper;
    private Map<String, Object> parameters = new HashMap<>();

    private ConfigurationBuilder () {}

    public <T> ConfigurationBuilder withParameter (ConfigurationParameter<T> parameter, T value) {
        this.parameters.put(parameter.name(), value);
        return this;
    }

    public ConfigurationBuilder withParameter (String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public ConfigurationBuilder withJsonMapper (Function<Object, String> jsonMapper) {
        this.jsonMapper = jsonMapper;
        return this;
    }

    public ConfigurationBuilder withResourceResolver (ResourceResolver resourceResolver) {
        this.resourceResolvers.add(resourceResolver);
        return this;
    }

    public ConfigurationBuilder withPropertyResolver (PropertyResolver propertyResolver) {
        this.propertyResolvers.add(propertyResolver);
        return this;
    }

    public ConfigurationBuilder overrideDefaults () {
        defaults = false;
        return this;
    }

    @Override
    public Configuration build() {
        Collection<PropertyResolver> propertyResolvers = new ArrayList<>();
        Collection<ResourceResolver> resourceResolvers = new ArrayList<>();

        if (defaults) {
            propertyResolvers.add(new FieldPropertyResolver(true));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.exactlyEqual()));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.prefixedEqual("get")));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.prefixedEqual("is")));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.prefixedEqual("has")));
            propertyResolvers.add(new MacroPropertyResolver());

            // Resource loaders
            resourceResolvers.add(new ClasspathResourceResolver(new ResourceLoader(getClass().getClassLoader())));
        }

        propertyResolvers.addAll(this.propertyResolvers);
        resourceResolvers.addAll(this.resourceResolvers);

        return new InternalConfiguration(
                JtwigParserBuilder.jtwigParser().withConfiguration(parserConfigurationBuilder.build()).build(),
                new CompositeResourceResolver(resourceResolvers),
                functionResolverBuilder.build(),
                new CompositePropertyResolver(propertyResolvers),
                jsonMapper,
                parameters
        );
    }

    private static class InternalConfiguration implements Configuration {
        private final JtwigParser jtwigParser;
        private final ResourceResolver resourceResolver;
        private final FunctionResolver functionResolver;
        private final PropertyResolver propertyResolver;
        private final Function<Object, String> jsonMapper;
        private final Map<String, Object> parameters;

        public InternalConfiguration(JtwigParser jtwigParser, ResourceResolver resourceResolver, FunctionResolver functionResolver, PropertyResolver propertyResolver, Function<Object, String> jsonMapper, Map<String, Object> parameters) {
            this.jtwigParser = jtwigParser;
            this.resourceResolver = resourceResolver;
            this.functionResolver = functionResolver;
            this.propertyResolver = propertyResolver;
            this.jsonMapper = jsonMapper;
            this.parameters = parameters;
        }

        @Override
        public JtwigParser parser() {
            return jtwigParser;
        }

        @Override
        public ResourceResolver resourceResolver() {
            return resourceResolver;
        }

        @Override
        public FunctionResolver functionResolver() {
            return functionResolver;
        }

        @Override
        public PropertyResolver propertyResolver() {
            return propertyResolver;
        }

        @Override
        public Function<Object, String> jsonMapper() {
            return jsonMapper;
        }

        @Override
        public <T> T parameter(String name, T defaultValue) {
            return (T) Optional.fromNullable(parameters.get(name)).or(defaultValue);
        }

        @Override
        public <T> T parameter(ConfigurationParameter<T> configurationParameter) {
            return parameter(configurationParameter.name(), configurationParameter.defaultValue());
        }
    }
}
