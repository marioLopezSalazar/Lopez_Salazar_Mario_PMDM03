package com.iesaguadulce.lopez_salazar_mario_pmdm03.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A utility class responsible for providing a Retrofit instance configured with the Pokémon API base URL and necessary converters.
 * This class ensures that a single instance of the ApiPokemonService is created and reused
 * throughout the application to make API requests to the Pokémon API.
 *
 * @author Mario López Salazar
 */
class ApiPokemonAdapter {

    /**
     * The API service to generate the HTTP requests.
     */
    private static ApiPokemonService API_SERVICE;

    /**
     * The base URL of the Pokémon API.
     */
    static final String BASE_URL = "https://pokeapi.co/api/v2/";


    /**
     * Provides the singleton instance of the ApiPokemonService.
     * If the API service has not been created yet, it initializes a Retrofit instance
     * with the base URL and creates the ApiPokemonService.
     *
     * @return An instance of ApiPokemonService used for API requests.
     */
    static ApiPokemonService getApiService() {

        // Creating the ApiPokemonService if it's not already initialized:
        if (API_SERVICE == null) {

            // Creating a new Retrofit instance with the base URL of the API and setting a GSON converter:
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Creating the ApiPokemonService:
            API_SERVICE = retrofit.create(ApiPokemonService.class);
        }

        return API_SERVICE;
    }
}
