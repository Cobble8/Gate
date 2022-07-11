package mod.gate.core.config;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class ConfigHolder {
    private final Field field;
    private final Object parent;
    private final Object defaultValue;

    public ConfigHolder(Object value, Field field, Object parent) {
        this.field = field;
        this.parent = parent;
        this.defaultValue = value;
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

    @Override
    public String toString() {
        return parent.getClass().getSimpleName() + "." + field.getName();
    }
}
