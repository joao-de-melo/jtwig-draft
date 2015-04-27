package org.jtwig;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;

public class SetTest extends AbstractIntegrationTest {

    @Test
    public void simpleSet() throws Exception {

        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% set variable = 1 %}{{ variable }}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("1"));
    }
}
