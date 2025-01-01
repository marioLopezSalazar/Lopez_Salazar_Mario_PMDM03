package com.iesaguadulce.lopez_salazar_mario_pmdm03.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.CardviewPokedexBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;

import java.util.List;


/**
 * Adapter class for displaying a list of Pokémon ID in the Pokédex RecyclerView.
 *
 * @author Mario López Salazar
 */
public class PokedexRecyclerviewAdapter extends RecyclerView.Adapter<PokedexViewHolder> {

    /**
     * Collection of Pokémon ID objects to be displayed in the RecyclerView.
     */
    private final List<PokemonId> pokemonList;

    /**
     * The execution context, usually the MainActivity.
     */
    private final Context context;


    /**
     * Constructs a new PokedexRecyclerView.
     *
     * @param pokemonList Collection of Pokémon ID objects to be displayed in the RecyclerView.
     * @param context     The execution context (normally MainActivity).
     */
    public PokedexRecyclerviewAdapter(List<PokemonId> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
    }


    /**
     * Called when RecyclerView needs a new PokedexViewHolder to display a Pokémon ID.
     * Inflates a new CardView and maintains its reference into the new PokedexViewHolder.
     * Note that it can be reused in the future to show some other pokemon.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View (normally CardView).
     * @return PokedexViewHolder referencing an inflated cardview_pokedex.
     */
    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflating a new CardView:
        CardviewPokedexBinding binding = CardviewPokedexBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        // Creating a ViewHolder which maintains reference to the CardView:
        return new PokedexViewHolder(binding);
    }


    /**
     * Called when RecyclerView needs to show a Pokémon ID in a PokedexViewHolder.
     * Sets the Pokémon ID into the CardView referenced by the PokedexViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the pokemon.
     * @param position The position of the Pokémon ID within the Pokémon List.
     */
    @Override
    public void onBindViewHolder(PokedexViewHolder holder, int position) {

        // Retrieving the Pokémon ID:
        PokemonId pokemon = pokemonList.get(position);

        // Setting the Pokémon ID into the CardView:
        holder.bind(pokemon);

        // Assigning a listener to handle the click event when the user will tap on the CardView:
        holder.itemView.setOnClickListener(view -> {

            // Avoiding immediate re-click:
            holder.itemView.setOnClickListener(null);

            // Requiring the Activity to catch de Pokémon:
            ((MainActivity) context).catchPokemon(pokemon);
        });
    }


    /**
     * Returns the total number of Pokémon ID in the list.
     *
     * @return Total number of Pokémon ID.
     */
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

}
