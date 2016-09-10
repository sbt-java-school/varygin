package ru.sbt;

import base.Apple;
import base.Person;
import base.Truck;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PluginManagerTest {
    private static final String rootLocalDirectory = "file:///D:/tmp/folder";
    private static Loader pluginManager;

    @BeforeClass
    public static void setUp() {
        try {
            pluginManager = new PluginManager(rootLocalDirectory);
        } catch (BusinessExceptions e) {
            throw new RuntimeException("Wrong root directory location");
        }
    }

    @Parameter
    public String expected;
    @Parameter(1)
    public String pluginName;
    @Parameter(2)
    public String className;

    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"mock:Person", "plugins", "mock.Person"},
                {"base:Person", "plugins", "base.Person"},
                {"base:Apple", "utils", "base.Apple"},
                {"mock:Truck", "utils", "mock.Truck"},
                {"base:Person", "utils", "base.Person"},
                {"mock:Person:Truck", "utils", "mock.Person"},
        });
    }

    @Test
    public void testLocal() {
        assertEquals("SimplePerson", new Person().action());
        assertEquals("SimpleTruck", new Truck().action());
        assertEquals("SimpleApple", new Apple().action());

        try {
            Plugin person = pluginManager.load(pluginName, className);
            assertEquals(expected, person.action());
        } catch (BusinessExceptions e) {
            fail(e.getMessage());
        }
    }
}
