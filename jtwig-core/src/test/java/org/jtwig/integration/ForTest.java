package org.jtwig.integration;

import org.jtwig.JtwigTemplate;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.JtwigModel.newModel;

public class ForTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleFor() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for i in list %}{{i}}{% endfor %}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is("12"));
    }

    @Test
    public void forWhiteSpaceControl() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate(" {%- for i in list -%} {{i}} {%- endfor -%} ");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is("12"));
    }

    @Test
    public void forIsolatedContextOldVariable() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% set a = 2 %}{% for i in list %}{% set a = 1 %}{% endfor %}{{ a }}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is("1"));
    }
    @Test
    public void forIsolatedContextNewVariable() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for i in list %}{% set a = 1 %}{% endfor %}{{ a }}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is(""));
    }

    @Test
    public void simpleForMap() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for k,v in list %}{{k}}={{v}} {% endfor %}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1,2}));

        assertThat(result, is("0=1 1=2 "));
    }

    @Test
    public void invalidForWithoutVariable() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for in list %}{{k}}={{v}} {% endfor %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting a variable name in for loop"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForWithoutListVariable() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for i in %}{{k}}={{v}} {% endfor %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting an expression in for loop"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForWithoutIn() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for i list %}{{k}}={{v}} {% endfor %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Malformed for loop, missing 'in' keyword. For example: {% for i in list %}"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForMissingEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for i in list ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Malformed for loop start syntax, missing code island ending symbol"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidEndForMissingEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for i in list %}{% endfor ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Malformed for loop end syntax, missing code island ending symbol"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForMissingSecondVariable() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for k, in list %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting a second variable name in for loop. Example: {% for key, value in list %}"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForMissingEndTag() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% for k in list %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing endfor tag"));

        jtwigTemplate.render(newModel());
    }
}
