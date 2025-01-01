package com.iesaguadulce.lopez_salazar_mario_pmdm03.pokedex;


import static com.iesaguadulce.lopez_salazar_mario_pmdm03.R.color.colorHint;
import static com.iesaguadulce.lopez_salazar_mario_pmdm03.R.color.colorSecondary;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.CardviewPokedexBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;


/**
 * Maintains reference to the visual elements of a CardView corresponding to a PokémonID.
 * Also allows setting the model data into the views.
 *
 * @author Mario López Salazar
 */
public class PokedexViewHolder extends RecyclerView.ViewHolder {

    /**
     * Reference to the visual elements of the CardView.
     */
    private final CardviewPokedexBinding binding;


    /**
     * Constructs a new ViewHolder object.
     *
     * @param binding ViewBinding corresponding to the CardView that will be stored in the new ViewHolder.
     */
    public PokedexViewHolder(CardviewPokedexBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    /**
     * Sets the Pokémon ID into the visual elements of the CardView.
     *
     * @param pokemon The Pokémon ID.
     */
    public void bind(PokemonId pokemon) {

        // Filling in the index & name of the Pokémon:
        binding.name.setText(String.format("#%s %s", pokemon.getIndex(), pokemon.getName()));

        // Performing different visual aspect for caught/uncaught Pokémon:
        if (pokemon instanceof Pokemon) {
            binding.pokeball.setVisibility(View.VISIBLE);
            binding.getRoot().setBackgroundColor(
                    ContextCompat.getColor(binding.getRoot().getContext(), colorHint));
        } else {
            binding.pokeball.setVisibility(View.GONE);
            binding.getRoot().setBackgroundColor(
                    ContextCompat.getColor(binding.getRoot().getContext(), colorSecondary));
        }
    }
}

