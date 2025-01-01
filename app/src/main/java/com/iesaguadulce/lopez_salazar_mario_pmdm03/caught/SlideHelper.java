package com.iesaguadulce.lopez_salazar_mario_pmdm03.caught;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;


/**
 * A helper class for enabling swipe gestures on the CaughtPokemon Recyclerview.
 * Specifically, it allows swiping items to the right to release Pokémon.
 *
 * @author Mario López Salazar
 */
public class SlideHelper extends ItemTouchHelper {


    /**
     * Constructs a SlideHelper for managing swipe gestures on a CaughtPokemon RecyclerView.
     *
     * @param context The context of the application, usually MainActivity.
     * @param adapter The RecyclerView adapter that handles Pokémon release logic.
     */
    public SlideHelper(Context context, CaughtRecyclerviewAdapter adapter) {

        super(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            // Disabling the drag-and-drop functionality:
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Launching the Pokémon release when right-swiped:
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.releasePokemon(viewHolder.getAdapterPosition());
            }

            // Enabling the swiping gesture only when releasing is allowed:
            @Override
            public boolean isItemViewSwipeEnabled() {
                return ((MainActivity) context).isReleaseAllowed();
            }
        });
    }
}