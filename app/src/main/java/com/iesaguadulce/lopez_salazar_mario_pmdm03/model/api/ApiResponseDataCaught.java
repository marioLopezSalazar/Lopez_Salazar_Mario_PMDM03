package com.iesaguadulce.lopez_salazar_mario_pmdm03.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the information of a Pokémon fetched from the Pokémon API using @GET("pokemon/{pokemonName}").
 * This class provides information about a Pokémon, including its weight, height, picture and types.
 * The data is structured to match the API's response format.
 *
 * @author Mario López Salazar
 */
class ApiResponseDataCaught {

    /**
     * Gets the Pokémon's weight.
     *
     * @return The weight in hectograms.
     */
    int getWeight() {
        return weight;
    }

    /**
     * Gets the Pokémon's height.
     *
     * @return The height in decimeters.
     */
    int getHeight() {
        return height;
    }

    /**
     * Gets the URL of the Pokémon's picture.
     *
     * @return The URL of the sprite from the "home" category.
     */
    String getPicture() {
        return sprites.other.home.front_default;
    }

    /**
     * Gets a list of the Pokémon's types.
     *
     * @return A list of strings representing the types of the Pokémon.
     */
    List<String> getTypes() {
        List<String> typeList = new ArrayList<>();
        for (TypeWrap type : types)
            typeList.add(type.type.name);
        return typeList;
    }


    // Attributes and classes to match the API response format:
    // ========================================================

    // WEIGHT:
    int weight;


    // HEIGHT:
    int height;


    //PICTURE:
    Sprites sprites;

    static class Sprites {
        Other other;
    }

    static class Other {
        Home home;
    }

    static class Home {
        String front_default;
    }


    // TYPES:
    List<TypeWrap> types;

    static class TypeWrap {
        Type type;
    }

    static class Type {
        String name;
    }

}
