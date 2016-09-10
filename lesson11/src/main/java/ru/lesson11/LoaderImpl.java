package ru.lesson11;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoaderImpl implements Loader {
    private String destination;
    private URLClassLoader loaderInstance;

    public LoaderImpl(String destination) throws BusinessExceptions {
        this.destination = destination;
        try {
            URLClassLoader loaderInstance = new URLClassLoader(new URL[]{new URL(destination)});
        } catch (MalformedURLException e) {
            throw new BusinessExceptions();
        }
    }

    public Object load(){

        return null;
    }
}
