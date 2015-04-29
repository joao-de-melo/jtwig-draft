package org.jtwig.integration;

import org.jtwig.JtwigTemplate;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;

public class IncludeTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void includingResourceFile() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% include 'classpath:/example/classpath-template.twig' %}");

        String result = template.render(newModel());

        assertThat(result, is("Hello"));
    }

    @Test
    public void includingResourceRelativeFile() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% include 'classpath:/example/classpath-include.twig' %}");

        String result = template.render(newModel());

        assertThat(result, is("Hello World"));
    }

    @Test
    public void includeMissingExpression() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% include %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Include missing path expression"));

        template.render(newModel());
    }

    @Test
    public void includeMissingEndCode() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% include 'bla' ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        template.render(newModel());
    }

    @Test
    public void includeMissingIgnoreMissing() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% include 'bla' ignore %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Did you mean 'ignore missing'?"));

        template.render(newModel());
    }

    @Test
    public void includeMissingMapOfValues() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% include 'bla' with %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting map of values"));

        template.render(newModel());
    }
}
