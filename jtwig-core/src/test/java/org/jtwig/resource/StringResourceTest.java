package org.jtwig.resource;

import com.google.common.base.Optional;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StringResourceTest {
    private StringResource underTest = new StringResource("one");

    @Test
    public void content() throws Exception {
        InputStream content = underTest.content();

        assertThat(IOUtils.toString(content), is("one"));
    }

    @Test
    public void relativePath() throws Exception {
        Optional<Resource> result = underTest.relative("one");

        assertThat(result.isPresent(), is(false));
    }
}