package com.iesaguadulce.lopez_salazar_mario_pmdm03.model;

import java.util.List;


/**
 * Represents a Pokémon with detailed information such as its picture, types, weight, and height.
 * This class extends PokemonId and includes additional attributes to provide a more complete representation of a Pokémon.
 *
 * @author Mario López Salazar
 */
public class Pokemon extends PokemonId {

    /**
     * The URL of the Pokémon's picture.
     */
    private String picture;

    /**
     * The list of types of the Pokémon.
     */
    private List<String> type;

    /**
     * The weight of the Pokémon in hectograms.
     */
    private int weight;

    /**
     * The height of the Pokémon in decimeters.
     */
    private int height;


    /**
     * Default constructor for creating an empty Pokémon object.
     * MUST NOT BE CALLED, only defined for GSON conversion by ApiPokemon.
     */
    public Pokemon() {
    }


    /**
     * Constructs a Pokémon with the specified details.
     *
     * @param name    The name of the Pokémon.
     * @param index   The index of the Pokémon in the Pokédex.
     * @param picture The URL of the Pokémon's picture.
     * @param type    The list of types of the Pokémon.
     * @param weight  The weight of the Pokémon in hectograms.
     * @param height  The height of the Pokémon in decimeters.
     */
    public Pokemon(String name, int index, String picture, List<String> type, int weight, int height) {
        super(name, index);
        this.picture = picture;
        this.type = type;
        this.weight = weight;
        this.height = height;
    }


    /**
     * Gets the URL of the Pokémon's picture.
     *
     * @return The picture URL.
     */
    public String getPicture() {
        return picture;
    }


    /**
     * Gets the list of the Pokémon's types.
     *
     * @return A list of strings representing the types of the Pokémon.
     */
    public List<String> getType() {
        return type;
    }


    /**
     * Gets the Pokémon's weight.
     *
     * @return The weight in hectograms.
     */
    public int getWeight() {
        return weight;
    }


    /**
     * Gets the Pokémon's height.
     *
     * @return The height in decimeters.
     */
    public int getHeight() {
        return height;
    }
}
