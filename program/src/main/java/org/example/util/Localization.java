package org.example.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization handler for multilingual support.
 */
public class Localization {
    private static ResourceBundle bundle;

    private Localization() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initializes localization bundle.
     * 
     * @param locale Target locale for translations
     */
    public static void init(Locale locale) {
        bundle = ResourceBundle.getBundle("locales.messages", locale);
    }

    /**
     * Gets localized string.
     * 
     * @param key Resource key
     * @return Localized string
     */
    public static String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Gets formatted localized string.
     * 
     * @param key Resource key
     * @param args Format arguments
     * @return Formatted localized string
     */
    public static String get(String key, Object... args) {
        return String.format(get(key), args);
    }
}
