package au.com.outware.caveman.data.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import au.com.outware.caveman.BuildConfig;

import static junit.framework.Assert.assertEquals;

/**
 * Because Environment relies on TextUtils.isEmpty()
 * SDK 21 because roboelectric doesnt support apis for a while
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EnvironmentTest {
    Environment environment;

    @Before
    public void setup() {
        environment = new Environment();
    }

    @Test
    public void whenKeyDoesntExist_defaultValueReturned() {
        // Act
        int value = environment.getProperty("test", 1);

        // Assert
        assertEquals(value, 1);
    }

    @Test(expected = RuntimeException.class)
    public void whenValueTypeUnsupported_exceptionIsThrown() {
        // Arrange
        environment.properties.put("test", new Environment());

        // Act
        environment.getProperty("test", new Environment());
    }

    @Test(expected = RuntimeException.class)
    public void whenValueTypeDoesntMatchDefaultArgument_exceptionIsThrown() {
        // Arrange
        environment.properties.put("whenValueTypeDoesntMatchDefaultArgument_exceptionIsThrown", 1);

        // Act
        boolean result = environment.getProperty("whenValueTypeDoesntMatchDefaultArgument_exceptionIsThrown", false);
    }

    @Test
    public void whenValidKey_valueIsReturned() {
        // Arrange
        environment.properties.put("test", 1);

        // Act
        int value = environment.getProperty("test", 2);

        // Assert
        assertEquals(value, 1);
    }

    @Test(expected = RuntimeException.class)
    public void whenSetInvalidType_exceptionIsThrown() {
        // Act
        environment.setProperty("test", new Environment());
    }

    @Test
    public void whenSetValidType_noErrors() {
        // Act
        environment.setProperty("test", 1);
    }
}
