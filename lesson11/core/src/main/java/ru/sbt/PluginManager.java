package ru.sbt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginManager implements Loader {
    private final String pluginRootDirectory;
    private static Map<String, Class<?>> pluginsMap = new HashMap<>();
    private static Map<String, URLClassLoader> loaderMap = new HashMap<>();

    public PluginManager(String pluginRootDirectory) throws BusinessExceptions {
        this.pluginRootDirectory = addSlashes(pluginRootDirectory);
    }

    public Plugin load(String pluginName, String pluginClassName) throws BusinessExceptions {
        try {
            String key = pluginName + "_" + pluginClassName;
            if (!pluginsMap.containsKey(key)) {
                pluginsMap.put(key, getUrlClassLoader(pluginName).loadClass(pluginClassName));
            }
            return (Plugin) pluginsMap.get(key).newInstance();
        } catch (IOException e) {
            throw new BusinessExceptions();
        } catch (ClassNotFoundException e) {
            throw new BusinessExceptions("Class " + pluginClassName + " haven't imported");
        } catch (IllegalAccessException | InstantiationException e) {
            throw new BusinessExceptions("Class " + pluginClassName + " is not plugin");
        }
    }

    private URLClassLoader getUrlClassLoader(String pluginName) throws MalformedURLException {
        String key = (pluginName != null && pluginName.length() > 0) ? pluginName : ".";
        if (!loaderMap.containsKey(key)) {
            URL url = new URL(pluginRootDirectory + addSlashes(pluginName));
            loaderMap.put(key, new MyURLClassLoader(new URL[]{url}));
        }
        return loaderMap.get(key);
    }

    private String addSlashes(String url) {
        if (url == null || url.length() == 0) {
            return url;
        }
        return (url.charAt(url.length() - 1) != '/') ? url + "/" : url;
    }

    private class MyURLClassLoader extends URLClassLoader {
        private MyURLClassLoader(URL[] urls) {
            super(urls);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                return findClass(name);
            } catch (ClassNotFoundException e) {
                return super.loadClass(name);
            }
        }
    }

}
