package org.jtwig;

import org.apache.commons.io.FileUtils;
import org.jtwig.resource.StringResource;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;

import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public abstract class AbstractIntegrationTest {

    @BeforeClass
    public static void setUpEnvironment() throws Exception {
        // Nasty hack to make classpath resources work on IntelliJ
        FileUtils.copyDirectory(new File("build/resources/intTest"), new File("build/resources/test"));
    }

    protected JtwigTemplate defaultStringTemplate(String content) {
        return new JtwigTemplate(new StringResource(content), configuration().build());
    }
}
