package org.jtwig;

import org.jtwig.resource.StringResource;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.emptyModel;
import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public class SimpleTest {
    @Test
    public void helloWorld() throws Exception {
        String result = new JtwigTemplate(new StringResource("{{ hello }}"), configuration().build())
                .render(emptyModel().define("hello", "world"));

        assertThat(result, is("world"));
    }
}
