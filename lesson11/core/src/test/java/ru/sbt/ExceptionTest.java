package ru.sbt;


import org.junit.Test;

public class ExceptionTest {

    @Test(expected = BusinessExceptions.class)
    public void testWrongRoot() throws BusinessExceptions {
        Loader pluginManager = new PluginManager("file:///D:/tmp");
        pluginManager.load("plugins", "mock.Person");
    }

    @Test(expected = BusinessExceptions.class)
    public void testWrongClassName() throws BusinessExceptions {
        Loader pluginManager = new PluginManager("file:///D:/tmp/folder");
        pluginManager.load("", "wrongClassName");
    }
}
