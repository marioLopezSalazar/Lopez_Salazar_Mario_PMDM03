package com.iesaguadulce.lopez_salazar_mario_pmdm03.caught;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.CardviewCaughtBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;

import java.util.List;


/**
 * Adapter class for displaying a list of Pokémon in the Caught Pokémon RecyclerView.
 *
 * @author Mario López Salazar
 */
public class CaughtRecyclerviewAdapter extends RecyclerView.Adapter<CaughtViewHolder> {

    /**
     * Collection of Pokémon objects to be displayed in the RecyclerView.
     */
    private final List<Pokemon> pokemonList;

    /**
     * The execution context, usually the MainActivity.
     */
    private final Context context;


    /**
     * Constructs a new CaughtPokemonRecyclerView.
     *
     * @param pokemonList Collection of Pokémon objects to be displayed in the RecyclerView.
     * @param context     The execution context (normally MainActivity).
     */
    public CaughtRecyclerviewAdapter(List<Pokemon> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;

    }

    /**
     * Called by the SlideHelper to release a Pokémon from the list at the specified position.
     * Updates the Caught Pokémon RecyclerView by removing the item and adjusting the remaining items.
     *
     * @param position The index of the Pokémon in the list to be released. Must be within the bounds of the list.
     */
    public void releasePokemon(int position) {
        if (position >= 0 && position < pokemonList.size()) {

            // Requesting the MainActivity to release the Pokémon:
            ((MainActivity) context).releasePokemon(pokemonList.get(position), task -> {

                // Once the Pokémon has been released:
                if (task.isSuccessful()) {

                    // Removing from the RecyclerView and adjusting the remaining items:
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, pokemonList.size() - position);
                }
            });
        }
    }


    /**
     * Called when RecyclerView needs a new CaughtViewHolder to display a Pokémon.
     * Inflates a new CardView and maintains its reference into the new CaughtViewHolder.
     * Note that it can be reused in the future to show some other Pokémon.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View (normally CardView).
     * @return CaughtViewHolder referencing an inflated cardview_caught.
     */
    @NonNull
    @Override
    public CaughtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflating a new CardView:
        CardviewCaughtBinding binding = CardviewCaughtBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        // Creating a ViewHolder which maintains reference to the CardView:
        return new CaughtViewHolder(binding);
    }


    /**
     * Called when RecyclerView needs to show a Pokémon in a CaughtViewHolder.
     * Sets the Pokémon information into the CardView referenced by the CaughtViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the Pokémon.
     * @param position The position of the Pokémon within the Pokémon List.
     */
    @Override
    public void onBindViewHolder(CaughtViewHolder holder, int position) {

        // Retrieving the Pokémon:
        Pokemon pokemon = pokemonList.get(position);

        // Setting the Pokémon information into the CardView:
        holder.bind(pokemon);

        // Assigning a listener to handle the click event when the user will tap on the CardView:
        holder.itemView.setOnClickListener(

                // Requiring the Activity to launch the Pokémon Detail Fragment:
                view -> ((MainActivity) context).launchPokemonDetail(pokemon));
    }


    /**
     * Returns the total number of Pokémon in the list.
     *
     * @return Total number of Caught Pokémon.
     */
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

}
