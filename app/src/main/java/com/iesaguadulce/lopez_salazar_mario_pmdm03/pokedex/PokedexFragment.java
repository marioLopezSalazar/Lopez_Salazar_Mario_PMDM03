package com.iesaguadulce.lopez_salazar_mario_pmdm03.pokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.FragmentPokedexBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;

import java.util.List;


/**
 * Fragment to display a RecyclerView containing a list of Pokémon ID.
 *
 * @author Mario López Salazar
 */
public class PokedexFragment extends Fragment {

    /**
     * ViewBinding to handle the view and access its elements.
     */
    private FragmentPokedexBinding binding;


    /**
     * Called to inflate the fragment's interface view.
     *
     * @param inflater           The LayoutInflater object used to inflate any views in the fragment.
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return The created view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Loads the RecyclerView with the Pokémon ID list.
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
        binding.pokedexRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Requesting the Pokémon ID list from the MainActivity:
        List<PokemonId> pokemonList = ((MainActivity) requireContext()).givePokedex();

        // Setting an Adapter to perform data binding (data model <--> recyclerview):
        binding.pokedexRecyclerview.setAdapter(new PokedexRecyclerviewAdapter(pokemonList, getActivity()));
    }
}