package com.iesaguadulce.lopez_salazar_mario_pmdm03.model.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Non-instantiable class for interacting with the Pokémon API.
 * Contains static methods to download the Pokédex and Pokémon information.
 * It uses Retrofit to make HTTP requests and handle the responses.
 *
 * @author Mario López Salazar
 */
public abstract class ApiPokemon {

    /**
     * Initiates the download of the Pokémon list from the API.
     *
     * @param offset The starting index for pagination. Must be greater than or equal to 0.
     * @param limit  The number of Pokémon to download. Must be greater than 0.
     * @return A task containing a list of PokémonID objects, or containing null if the Pokédex is unavailable.
     * This allows the caller to know when the Pokédex is ready for use.
     */
    public static Task<List<PokemonId>> downloadPokedex(int offset, int limit) {

        TaskCompletionSource<List<PokemonId>> task = new TaskCompletionSource<>();

        // Managing incorrect parameters values:
        if (offset < 0 || limit <= 0) {
            task.setResult(null);
            return task.getTask();
        }

        // Creating and launching the HTTP call:
        Call<ApiResponseDataPokedex> call = ApiPokemonAdapter.getApiService().getPokemonList(offset, limit);
        call.enqueue(new Callback<ApiResponseDataPokedex>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponseDataPokedex> call, @NonNull Response<ApiResponseDataPokedex> response) {

                if (response.isSuccessful() && response.body() != null)
                    // When OK response received, extracts the result and sets it on the task result:
                    task.setResult(response.body().getResults());
                else
                    // Otherwise, sets null on the task result:
                    task.setResult(null);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDataPokedex> call, @NonNull Throwable t) {
                task.setResult(null);
            }
        });

        return task.getTask();
    }


    /**
     * Initiates the download of full information of a Pokémon.
     *
     * @param pokemonId The ID of the Pokémon to be caught.
     * @return A task containing the full Pokémon information, or containing null if the information is unavailable.
     * This allows the caller to know when the Pokémon information is ready for use.
     */
    public static Task<Pokemon> catchPokemon(@NonNull PokemonId pokemonId) {

        TaskCompletionSource<Pokemon> task = new TaskCompletionSource<>();

        // Creating and launching the HTTP call:
        Call<ApiResponseDataCaught> call = ApiPokemonAdapter.getApiService().getPokemon(pokemonId.getName().toLowerCase());
        call.enqueue(new Callback<ApiResponseDataCaught>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponseDataCaught> call, @NonNull Response<ApiResponseDataCaught> response) {

                if (response.isSuccessful() && response.body() != null)
                    // When OK response received, extracts the result and sets it on the task result:
                    task.setResult(
                            new Pokemon(
                                    pokemonId.getName(),
                                    pokemonId.getIndex(),
                                    response.body().getPicture(),
                                    response.body().getTypes(),
                                    response.body().getWeight(),
                                    response.body().getHeight())
                    );
                else
                    // Otherwise, sets null on the task result:
                    task.setResult(null);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDataCaught> call, @NonNull Throwable t) {
                task.setResult(null);
            }
        });

        return task.getTask();
    }

}
