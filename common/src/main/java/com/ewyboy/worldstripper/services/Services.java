package com.ewyboy.worldstripper.services;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = loadService(IPlatformHelper.class);

    /**
     * Load a service implementation
     *
     * @param service the service class
     * @param <T>     the service type
     * @return the service implementation
     */
    public static <T> T loadService(Class<T> service) {
        return ServiceLoader.load(service).findFirst().orElseThrow(() -> new IllegalStateException("No implementation found for " + service.getName()));
    }

}
