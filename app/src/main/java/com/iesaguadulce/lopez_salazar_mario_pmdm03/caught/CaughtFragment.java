package com.iesaguadulce.lopez_salazar_mario_pmdm03.caught;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.FragmentCaughtBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;

import java.util.List;


/**
 * Fragment to display a RecyclerView containing a list of caught pokemon.
 *
 * @author Mario López Salazar
 */
public class CaughtFragment extends Fragment {

    /**
     * ViewBinding to handle the view and access its elements.
     */
    private FragmentCaughtBinding binding;


    /**
     * Called to inflate the fragment's interface view.
     *
     * @param inflater           The LayoutInflater object used to inflate any views in the fragment.
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The created view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCaughtBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Loads the RecyclerView with the caught pokemon list.
     * This method is called after the view has been created.
     *
     * @param view               The View, returned by onCreateView method.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing the RecyclerView with a LayoutManager to manage its visual elements:
        binding.caughtRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Requesting the Caught Pokemon collection from the MainActivity:
        List<Pokemon> pokemonList = ((MainActivity) requireContext()).giveCaughtPokemon();

        // Setting an Adapter to perform data binding (data model <--> recyclerview):
        CaughtRecyclerviewAdapter adapter = new CaughtRecyclerviewAdapter(pokemonList, getActivity());
        binding.caughtRecyclerview.setAdapter(adapter);

        // Setting an SlideHelper to perform Pokemon deleting:
        SlideHelper slideHelper = new SlideHelper(requireContext(), adapter);
        slideHelper.attachToRecyclerView(binding.caughtRecyclerview);
    }
}