package mod.gate.core.config;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

import static mod.gate.utils.ObjectUtils.cloneObject;

public class ConfigHolder {
    private final Field field;
    private final Object parent;
    private final Object defaultValue;

    public ConfigHolder(Object value, Field field, Object parent) {
        this.field = field;
        this.parent = parent;
        Object clone = cloneObject(value);
        this.defaultValue = clone == null ? value : clone ;
    }

    public Field getField() {
        return field;
    }

    public Object getParent() {
        return parent;
    }

    public void setConfig(Object value) {
        try {
            FieldUtils.writeField(field, parent, value, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void loadDefault() {
        setConfig(this.defaultValue);
    }

    public Object getDefault() {
        return this.defaultValue;
    }

    public Object get(String fieldName) {
        try {
            return FieldUtils.readField(field.getType().getField(fieldName), field.get(parent), true);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(String fieldName, Object value) {
        try {
            FieldUtils.writeField(field.getType().getField(fieldName), field.get(parent), value, true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return parent.getClass().getSimpleName() + "." + field.getName();
    }
}
