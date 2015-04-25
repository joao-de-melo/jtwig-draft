package org.jtwig;

import org.jtwig.resource.StringResource;

import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public abstract class AbstractIntegrationTest {

    protected JtwigTemplate defaultStringTemplate(String content) {
        return new JtwigTemplate(new StringResource(content), configuration().build());
    }
}
