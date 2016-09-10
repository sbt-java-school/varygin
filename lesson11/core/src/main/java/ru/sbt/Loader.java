package ru.sbt;

public interface Loader {
    public Plugin load(String pluginName, String pluginClassName) throws BusinessExceptions;
}
