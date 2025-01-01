package com.iesaguadulce.lopez_salazar_mario_pmdm03.model;

/**
 * Represents a Pokémon's basic identifier, consisting of its name and index in the Pokédex.
 * This class provides methods to access the Pokémon's name and index,
 * and implements comparison and equality checks for sorting and matching purposes.
 *
 * @author Mario López Salazar
 */
public class PokemonId implements Comparable<PokemonId> {

    /**
     * The name of the Pokémon.
     */
    private String name;

    /**
     * The index of the Pokémon in the Pokédex.
     */
    private int index;


    /**
     * Default constructor for creating an empty PokémonId object.
     * MUST NOT BE CALLED, only defined for GSON conversion by ApiPokemon.
     */
    public PokemonId() {
    }


    /**
     * Constructs a Pokémon ID with the specified name and index.
     *
     * @param name  The name of the Pokémon.
     * @param index The index of the Pokémon in the Pokédex.
     */
    public PokemonId(String name, int index) {
        this.name = name;
        this.index = index;
    }


    /**
     * Gets the name of the Pokémon.
     *
     * @return The Pokémon's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the index of the Pokémon in the Pokédex.
     *
     * @return The Pokémon's index.
     */
    public int getIndex() {
        return index;
    }


    /**
     * Checks if this Pokémon ID is equal to another object.
     * Two Pokémon IDs are considered equal if their indexes are the same.
     *
     * @param o The object to compare with.
     * @return True if the object is a PokemonId with the same index, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof PokemonId)
                && (this.index == ((PokemonId) o).index);
    }


    /**
     * Compares this Pokémon ID with another for sorting purposes.
     * Pokémon are compared by their index in ascending order.
     *
     * @param pokemonID The Pokémon ID to compare with.
     * @return A negative integer, zero, or a positive integer if this Pokémon's index
     * is less than, equal to, or greater than the specified Pokémon's index.
     */
    @Override
    public int compareTo(PokemonId pokemonID) {
        return Integer.compare(index, pokemonID.index);
    }
}
