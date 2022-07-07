package mod.gate.events;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    private static final List<CallbackEvent> events = new ArrayList<>();

    public static void registerEvent(Object feature) {
        for (Method eventMethod : MethodUtils.getMethodsWithAnnotation(feature.getClass(), Event.class)) {
            events.add(new CallbackEvent(
                    MethodUtils.getAnnotation(eventMethod, Event.class, false, true).event(),
                    eventMethod,
                    feature)
            );
        }
    }

    public static void run(Object eventClass) {
        for (CallbackEvent event : events) {
            if (event.isEventClass(eventClass.getClass())) {
                try {
                    MethodUtils.invokeMethod(event.getFeature(), true, event.getEventMethod().getName(), eventClass);
                } catch (Exception e) {
                    // Nothing currently, should make it log something
                }
            }
        }
    }

    public static class CallbackEvent {
        private final Class<?> eventClass;
        private final Method eventMethod;
        private final Object feature;

        public CallbackEvent(Class<?> eventClass, Method eventMethod, Object feature) {
            this.eventClass = eventClass;
            this.eventMethod = eventMethod;
            this.feature = feature;
        }

        public boolean isEventClass(Class<?> eventClass) {
            return eventClass == this.eventClass;
        }

        public Method getEventMethod() {
            return eventMethod;
        }

        public Object getFeature() { return feature; }
    }
}
