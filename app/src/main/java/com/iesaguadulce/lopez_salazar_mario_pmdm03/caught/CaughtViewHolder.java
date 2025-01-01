package com.iesaguadulce.lopez_salazar_mario_pmdm03.caught;

import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.CardviewCaughtBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.LocalizationTools;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.Locale;


/**
 * Maintains reference to the visual elements of a CardView corresponding to a caught pokemon.
 * Also allows setting the model data into the views.
 *
 * @author Mario López Salazar
 */
public class CaughtViewHolder extends RecyclerView.ViewHolder {

    /**
     * Reference to the visual elements of the CardView.
     */
    private final CardviewCaughtBinding binding;


    /**
     * Constructs a new ViewHolder object.
     *
     * @param binding ViewBinding corresponding to the CardView that will be stored in the new ViewHolder.
     */
    public CaughtViewHolder(CardviewCaughtBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    /**
     * Sets the caught pokemon data into the visual elements of the CardView.
     *
     * @param pokemon The pokemon.
     */
    public void bind(Pokemon pokemon) {

        //Filling in the fields with the pokemon's information (includes the picture downloading):
        Picasso.get().load(pokemon.getPicture()).into(binding.image);
        String index_name = String.format(Locale.getDefault(), "#%d %s",
                pokemon.getIndex(),
                pokemon.getName());
        binding.name.setText(index_name);
        binding.pokeball.setContentDescription(index_name);
        binding.type.setText(LocalizationTools.localizeTypes(pokemon.getType()));

        // Forcing the refreshment of the visual elements:
        binding.executePendingBindings();
    }
}
