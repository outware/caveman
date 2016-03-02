package au.com.outware.caveman.data.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an execution {@link Environment} for the client app. Contains a set of properties that
 * should be used for the client app configuration (eg. urls or flags). One client app should have
 * more than one {@link Environment}, and at least a production one.
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public final class Environment {
    public static final String ENVIRONMENT_PRODUCTION = "Production";

    String name;
    Map<String, Object> properties = new HashMap<>();

    public Environment() { }

    /**
     * Returns <code>true</code> if this is the production {@link Environment}.
     *
     * @return <code>true</code> if this is the production {@link Environment}, <code>false</code>
     * otherwise.
     */
    public boolean isProduction() {
        return !TextUtils.isEmpty(name) && name.equalsIgnoreCase(ENVIRONMENT_PRODUCTION);
    }

    /**
     * Get the value of a property in this {@link Environment} based on the key.
     *
     * @param key
     *         the key of the desired property.
     * @param defaultValue
     *         the value that will be returned if the property is <code>null</code> or the key was
     *         not found.
     *
     * @return the value related to the specified key. If the property is <code>null</code>, the
     * key is not found or the type is not a valid JSON type the given defaultValue will be returned.
     */
    public <T> T getProperty(@NonNull final String key, final T defaultValue) throws RuntimeException {
        T value = defaultValue;

        if (!TextUtils.isEmpty(key) && properties != null) {
            Object property = properties.get(key);

            if (property == null) {
                value = defaultValue;
            } else {
                Class clazz = defaultValue.getClass();
                if(Number.class.isAssignableFrom(clazz)) {
                    Number numberProperty = (Number) property;
                    if(clazz == Integer.class) {
                        value = (T) Integer.valueOf(numberProperty.intValue());
                    } else if(clazz ==  Float.class) {
                        value = (T) Float.valueOf(numberProperty.floatValue());
                    } else if(clazz == Double.class) {
                        value = (T) Double.valueOf(numberProperty.doubleValue());
                    } else if (clazz == Long.class) {
                        value = (T) Long.valueOf(numberProperty.longValue());
                    } else if (clazz == Short.class) {
                        value = (T) Short.valueOf(numberProperty.shortValue());
                    } else if (clazz == Byte.class) {
                        value = (T) Byte.valueOf(numberProperty.byteValue());
                    }
                } else if(String.class == clazz || Boolean.class == clazz) {
                    value = (T) property;
                } else {
                    throw new RuntimeException("Value must be of type Number, String or Boolean");
                }
            }
        }

        return value;
    }

    public <T> void setProperty(@NonNull final String key, T value) {
        Class clazz = value.getClass();

        if(Number.class.isAssignableFrom(clazz) || String.class == clazz || Boolean.class == clazz) {
            properties.put(key, value);
        } else {
            throw new RuntimeException("Value must be of type Number, String or Boolean");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object object) {
        return object != null &&
                object.getClass().equals(Environment.class) &&
                name.equalsIgnoreCase(((Environment) object).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}