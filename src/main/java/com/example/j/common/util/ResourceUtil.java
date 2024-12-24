package com.example.j.common.util;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;


@Component
public class ResourceUtil implements ResourceLoaderAware {
    private static ResourceLoader resourceLoader;

    private ResourceUtil() {
    }

    public static InputStream load(String location) throws IOException {
        InputStream in;
        Resource    resource = resourceLoader.getResource(location);
        in = resource.getInputStream();
        return in;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        ResourceUtil.resourceLoader = resourceLoader;
    }
}
