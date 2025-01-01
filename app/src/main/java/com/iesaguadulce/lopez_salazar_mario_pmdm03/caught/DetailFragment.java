package com.iesaguadulce.lopez_salazar_mario_pmdm03.caught;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iesaguadulce.lopez_salazar_mario_pmdm03.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.R;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.FragmentDetailBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;
import com.squareup.picasso.Picasso;


/**
 * Fragment to display Pokémon details.
 *
 * @author Mario López Salazar
 */
public class DetailFragment extends Fragment {

    /**
     * ViewBinding to handle the view and access its elements.
     */
    private FragmentDetailBinding binding;


    /**
     * Called to inflate the fragment's interface view.
     *
     * @param inflater           The LayoutInflater object used to inflate any views in the fragment.
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Called after the view has been created. Fills in the UI elements with the Pokémon's information.
     *
     * @param view               The View, returned by onCreateView method.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting Pokemon information from the Bundle object (set when navigating to this fragment):
        if (getArguments() != null) {

            // Filling in the index & name:
            String pokemon = String.format("#%s %s",
                    getArguments().getInt("index"),
                    getArguments().getString("name"));
            binding.indexAndName.setText(pokemon);

            // Filling in the type/s:
            binding.type.setText(getArguments().getString("type"));

            // HTML formatting and filling in the weight:
            String formatted = String.format("<b>%s:</b> %s",
                    getString(R.string.weight),
                    getArguments().getString("weight"));
            binding.weight.setText(Html.fromHtml(formatted, Html.FROM_HTML_MODE_LEGACY));

            // HTML formatting and filling in the height:
            formatted = String.format("<b>%s:</b> %s",
                    getString(R.string.height),
                    getArguments().getString("height"));
            binding.height.setText(Html.fromHtml(formatted, Html.FROM_HTML_MODE_LEGACY));

            // Downloading and setting the picture:
            Picasso.get().load(getArguments().getString("picture")).into(binding.picture);

            // Setting up the Release button (depending on if the releasing option is enabled/disabled):
            if (getArguments().getBoolean("release_allowed"))
                binding.release.setOnClickListener(this::releasePokemon);
            else
                binding.release.setVisibility(View.GONE);

            // Setting up the ActionBar title (index & name of the Pokémon):
            AppCompatActivity activity;
            ActionBar actionBar;
            if ((activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
                actionBar.setTitle(pokemon);
        }

        // When Bundle object encapsulating Pokémon information cannot be found:
        else {
            // Displaying a Toast message informing the fail:
            Toast.makeText(requireContext(), getString(R.string.noDataPokemon), Toast.LENGTH_SHORT).show();

            // Returning to Caught Pokémon List:
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        }
    }


    /**
     * Called when user taps on the Release Button.
     * Requires the MainActivity to release the Pokémon and navigates up when done.
     *
     * @param view The view where the Release Button was tapped.
     */
    private void releasePokemon(View view) {

        // Managing if the Pokémon information is not available:
        if (getArguments() == null) {
            // Displaying a Toast message informing the fail:
            Toast.makeText(requireContext(), getString(R.string.noDataPokemon), Toast.LENGTH_SHORT).show();

            // Returning to Caught Pokémon List:
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        }

        // Requesting the MainActivity to release the Pokémon:
        ((MainActivity) requireActivity()).releasePokemon(
                new PokemonId(
                        getArguments().getString("name"),
                        getArguments().getInt("index")),
                task -> {
                    if (task.isSuccessful()) {
                        // Returning to previous fragment:
                        NavController navController = NavHostFragment.findNavController(this);
                        navController.popBackStack();
                    }
                }
        );
    }

}
