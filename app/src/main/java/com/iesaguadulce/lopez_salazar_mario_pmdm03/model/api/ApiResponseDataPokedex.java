package com.iesaguadulce.lopez_salazar_mario_pmdm03.model.api;

import androidx.annotation.NonNull;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.LocalizationTools;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a list of Pokémon ID fetched from the Pokémon API using @GET("pokemon").
 * Pokémon name is capitalized, and also Pokémon index is got from the specific Pokémon API URL.
 *
 * @author Mario López Salazar
 */
class ApiResponseDataPokedex {

    /**
     * Provides the list of Pokémon ID retrieved from the API.
     *
     * @return The list of Pokémon ID.
     */
    List<PokemonId> getResults() {

        List<PokemonId> pokemonList = new ArrayList<>();

        // Extracting and building each element from the Result collection:
        for (Result result : results)
            pokemonList.add(fromResult(result));
        return pokemonList;
    }


    /**
     * Obtains a PokemonId object from a Result object.
     * Pokémon name is capitalized, and also Pokémon index is got from the specific Pokémon API URL.
     *
     * @param result A Result object, fetched from the HTTP request.
     * @return A PokemonId object containing the elementary information of the Pokémon.
     */
    private static PokemonId fromResult(@NonNull Result result) {

        String capitalizedName = LocalizationTools.capitalize(result.name);

        String[] tokens = result.url.split("/");
        int index = Integer.parseInt(tokens[tokens.length - 1]);

        return new PokemonId(capitalizedName, index);
    }


    // Attributes and classes to match the API response format:
    // ========================================================

    List<Result> results;

    static class Result {
        String name;
        String url;
    }
}



