package com.iesaguadulce.lopez_salazar_mario_pmdm03.model.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface which defines the endpoints for interacting with the Pokémon API.
 * Used by Retrofit to generate the necessary HTTP requests for retrieving Pokémon data.
 *
 * @author Mario López Salazar
 */
interface ApiPokemonService {

    /**
     * Retrieves a paginated list of Pokémon from the Pokémon API.
     *
     * @param offset The starting index for pagination. Must be greater than or equal to 0.
     * @param limit  The number of Pokémon to retrieve. Must be greater than 0.
     * @return A Call object that, when executed, performs the HTTP request and returns an ApiResponseDataPokedex
     * object containing the Pokémon ID list.
     */
    @GET("pokemon")
    Call<ApiResponseDataPokedex> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);


    /**
     * Retrieves detailed information about a specific Pokémon from the Pokémon API.
     *
     * @param pokemonName The name of the Pokémon to retrieve.
     * @return A Call object that, when executed, performs the HTTP request and returns an ApiResponseDataCaught
     * object containing the Pokémon information.
     */
    @GET("pokemon/{pokemonName}")
    Call<ApiResponseDataCaught> getPokemon(@Path("pokemonName") String pokemonName);
}



