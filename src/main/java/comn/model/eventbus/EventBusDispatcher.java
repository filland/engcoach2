package comn.model.eventbus;

import java.util.HashMap;
import java.util.Map;

public enum EventBusDispatcher {
    INSTANCE;

    private Map<Class<?>, Class<?>> services = new HashMap<>();
    private Map<Class<?>, Object> cache = new HashMap<>();

    public <T> void registerService(Class<T> service, Class<? extends T> provider) {

        services.put(service, provider);
    }

    public <T> T getService(Class<T> type) {

        Class<?> provider = services.get(type);

        try {

            if (cache.containsKey(type)) {

                return (T) cache.get(type);

            } else {

                Object instance = provider.getConstructor().newInstance();
                cache.put(type, instance);

                return (T) instance;
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Service is not available - " + type);
        }
    }


}
