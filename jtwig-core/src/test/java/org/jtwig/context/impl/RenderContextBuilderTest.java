package org.jtwig.context.impl;

import org.jtwig.JtwigModel;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RenderContextBuilderTest {

    @Test
    public void buildShouldSetAModel() throws Exception {
        RenderContext context = RenderContextBuilder.renderContext().build();
        assertThat(context.model(), notNullValue());
    }

    @Test
    public void buildShouldSetTheSameConfiguration() throws Exception {
        Configuration configuration = mock(Configuration.class);
        RenderContext context = RenderContextBuilder.renderContext()
            .withConfiguration(configuration)
            .build();

        assertThat(context.configuration(), is(configuration));
    }

    @Test
    public void buildShouldSetTheSameModel() throws Exception {
        JtwigModel jtwigModel = mock(JtwigModel.class);
        RenderContext context = RenderContextBuilder.renderContext()
            .withModel(jtwigModel)
            .build();

        assertThat(context.model(), is(jtwigModel));
    }
}