package com.iesaguadulce.lopez_salazar_mario_pmdm03.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Provides utility methods for fancying Pokémon data such as name, weight, height and types.
 * Contains static methods to format measurements and localize Pokémon types into the desired language and format.
 *
 * @author Mario López Salazar
 */
public abstract class LocalizationTools {

    /**
     * Fancies a Pokémon's weight for display based on its value in hectograms.
     *
     * @param weightHg The weight in hectograms.
     * @return A formatted string representing the weight in kilograms or grams.
     */
    public static String localizateWeight(int weightHg) {
        return (weightHg < 10)
                ? weightHg * 100 + " g"
                : String.format(Locale.getDefault(), "%.2f kg", weightHg / 10.0);
    }


    /**
     * Fancies a Pokémon's height for display based on its value in decimeters.
     *
     * @param heightdm The height in decimeters.
     * @return A formatted string representing the height in meters or centimeters.
     */
    public static String localizateHeight(int heightdm) {
        return (heightdm < 10)
                ? heightdm * 10 + " cm"
                : String.format(Locale.getDefault(), "%.2f m", heightdm / 10.0);
    }


    /**
     * A map for allocating Pokémon types in selected language.
     */
    private static Map<String, String> localTypes;


    /**
     * Establishes a new map of Pokémon types for localization.
     *
     * @param types A map where keys are type names in English, and values are their localized equivalents.
     */
    public static void setLocalTypes(Map<String, String> types) {
        localTypes = types;
    }


    /**
     * Localizes a single Pokémon type.
     * If the type cannot be localized, the original type is returned with its first letter capitalized.
     *
     * @param type The name of the type in English.
     * @return The localized type name, or the original name if no localization is available.
     */
    @NonNull
    private static String localizeType(String type) {

        // Localization map is available:
        if (localTypes != null) {
            String localType = localTypes.get(type);
            if (localType != null)
                // Type found in map:
                return capitalize(localType);
            else
                // Type not found in map:
                return capitalize(type);
        }

        // Localization map is not available:
        else
            return capitalize(type);
    }


    /**
     * Localizes a list of Pokémon types into a single formatted string.
     * If the localization map is not set up, the types will be returned as they are.
     * Types are separated by " // ".
     *
     * @param types A list of type names in English.
     * @return A formatted string containing the localized type names, separated by " // ".
     */
    public static String localizeTypes(List<String> types) {
        StringBuilder typesString;

        // Localization map is available:
        if (localTypes != null) {

            // First type in the list:
            typesString = new StringBuilder(localizeType(types.get(0)));

            // The rest of the types in the list:
            for (int i = 1; i < types.size(); i++)
                typesString.append(" // ").append(localizeType(types.get(i)));
        }

        // Localization map is not available:
        else {

            // First type in the list:
            typesString = new StringBuilder(types.get(0));

            // The rest of the types in the list:
            for (int i = 1; i < types.size(); i++)
                typesString.append(" // ").append(types.get(i));
        }

        return typesString.toString();
    }


    /**
     * Capitalizes the first letter of a string.
     *
     * @param string The string to capitalize.
     * @return The string with its first letter converted to uppercase.
     */
    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
