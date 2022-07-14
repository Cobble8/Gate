package mod.gate.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectUtils {

    //originally based on https://stackoverflow.com/a/25338780 under the CC BY-SA 3.0 license
    public static <T> T cloneObject(T object) {
        try {
            T clone = (T) object.getClass().getConstructor().newInstance();

            for (Field field: object.getClass().getDeclaredFields()) {
                cloneField(field, object, clone);
            }
            //this is untested
            if (object.getClass().getSuperclass() != null) {
                for (Field field: object.getClass().getSuperclass().getDeclaredFields()) {
                    cloneField(field, object, clone);
                }
            }

            return clone;
        }catch (Exception e) {
            return null;
        }
    }

    private static <T> void cloneField(Field field, T object, T clone) throws IllegalAccessException {
        field.setAccessible(true);

        if (field.get(object) == null || Modifier.isFinal(field.getModifiers())) return;

        if (field.getType().isPrimitive()
                || field.getType().equals(String.class)
                || (field.getType().getSuperclass() != null && Number.class.equals(field.getType().getSuperclass()))
                || field.getType().equals(Boolean.class)) {
            field.set(clone, field.get(object));
        } else {
            Object childObject = field.get(object);

            if (childObject == object) {
                field.set(clone, clone);
            } else {
                field.set(clone, cloneObject(field.get(object)));
            }
        }
    }
}
