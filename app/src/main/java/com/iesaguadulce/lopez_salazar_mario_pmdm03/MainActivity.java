package com.iesaguadulce.lopez_salazar_mario_pmdm03;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.databinding.ActivityMainBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.LocalizationTools;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.api.ApiPokemon;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Main Activity of the APP.
 * Manages user interactions, UI setup, data loading, and navigation between fragments.
 *
 * @author Mario López Salazar
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Binding object to access views.
     */
    private ActivityMainBinding binding;

    /**
     * NavController to handle navigation.
     */
    private NavController navController;

    /**
     * Authenticated Firebase user.
     */
    private FirebaseUser user;

    /**
     * List of Pokémon from the Pokédex.
     */
    private List<PokemonId> pokedex;

    /**
     * List of caught Pokémon.
     */
    private List<Pokemon> caughtPokemon;

    /**
     * Flag to determine if Pokémon release is allowed.
     */
    private boolean releaseAllowed = false;

    /**
     * Values for API pagination.
     */
    public static final int POKEDEX_OFFSET = 0;
    private static int pokedexLimit;


    /**
     * Called when the activity is created. Sets up the UI, loads shared preferences, and
     * initializes language and data loading.
     *
     * @param savedInstanceState If the activity is being re-initialized, contains the data from the last instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading and applying shared preferences:
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // ALLOW RELEASE
        releaseAllowed = preferences.getBoolean("release", false);

        // LIGHT/NIGHT THEME
        SettingsFragment.applyTheme(preferences.getString("light_night", "system"));

        // --> Calling super.onCreate() after setting light/night theme:
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // POKEDEX SIZE:
        pokedexLimit = Integer.parseInt(preferences.getString("pokedex_count", "151"));

        // LANGUAGE:
        boolean english = preferences.getBoolean("english", false);

        SettingsFragment.changeLanguage(this,
                        english ? "en" : "es")
                .addOnCompleteListener(this::loadData);
    }


    /**
     * Loads data from API and Firestore, and updates the UI accordingly.
     *
     * @param taskSetLanguage Task that completes when language has been set.
     */
    private void loadData(Task<Void> taskSetLanguage) {

        // Current Firebase logged in user:
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Launching Pokédex download from API and Caught Pokémon from Firestore:
        Task<List<PokemonId>> downloadPokedex = ApiPokemon.downloadPokedex(POKEDEX_OFFSET, pokedexLimit);
        Task<List<Pokemon>> getCaughtPokemon = Database.getCaughtPokemon(user);

        Tasks.whenAllComplete(downloadPokedex, getCaughtPokemon)
                .addOnCompleteListener(finished -> {

                    StringBuilder status = new StringBuilder();
                    boolean bothTasksOk = true;

                    // A. Retrieving caught Pokémon list:
                    caughtPokemon = getCaughtPokemon.getResult();
                    if (caughtPokemon == null) {
                        caughtPokemon = new ArrayList<>();
                        status.append(getString(R.string.no_data_pokemon_list));
                        bothTasksOk = false;
                    } else if (caughtPokemon.isEmpty())
                        status.append(getString(R.string.no_caught_pokemon));
                    else if (caughtPokemon.size() == 1)
                        status.append(getString(R.string.one_caught_pokemon));
                    else
                        status.append(String.format("%s %s %s",
                                getString(R.string.you_already_have),
                                caughtPokemon.size(),
                                getString(R.string.caught_pokemon)));

                    // B. Retrieving Pokédex:
                    if (downloadPokedex.isSuccessful() && downloadPokedex.getResult() != null)
                        pokedex = downloadPokedex.getResult();

                    else {
                        pokedex = new ArrayList<>();
                        status.append(getString(R.string.pokedex_unavailable));
                        bothTasksOk = false;
                    }

                    if (bothTasksOk)
                        // Indicating which Pokémon in the Pokédex have been previously caught:
                        lockCaughtPokemon(caughtPokemon);

                    // Launching view initialization:
                    initializeView(status.toString());
                });
    }


    /**
     * Initializes the main UI components, such as navigation and showing status messages.
     *
     * @param status Status message to display to the user.
     */
    private void initializeView(String status) {

        // Inflating the main view:
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setting up the ToolBar as the default ActionBar:
        setSupportActionBar(binding.toolbar);

        // Linking the BottomNavigation and the NavController:
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(binding.fragmentContainer.getId());
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        // Setting up a listener for the BottomNavigation MenuItems:
        binding.bottomNavigation.setOnItemSelectedListener(this::bottomNavigationOptionSelected);

        // Managing the visibility of the ActionBar and the BottomNavigation:
        navController.addOnDestinationChangedListener(this::onDestinationChanged);

        // Showing the result of the data load:
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
    }


    /**
     * Handles navigation when a BottomNavigation item is selected.
     *
     * @param menuItem The selected menu item.
     * @return True if the item is handled by NavigationUI; false otherwise.
     */
    private boolean bottomNavigationOptionSelected(MenuItem menuItem) {
        // Navigating to the Fragment whose ID coincides with the MenuItem ID:
        return NavigationUI.onNavDestinationSelected(menuItem, navController);
    }


    /**
     * Manages changes in navigation destination, updating the ActionBar and BottomNavigation visibility.
     *
     * @param controller  NavController managing the navigation.
     * @param destination The current NavDestination.
     * @param arguments   Additional arguments passed to the fragment.
     */
    private void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {

        int destinationId = destination.getId();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            // ActionBar visible/hidden:
            if ((destinationId == R.id.detailFragment) || (destinationId == R.id.settingsFragment))
                actionBar.show();
            else
                actionBar.hide();

            // ActionBar title:
            actionBar.setTitle(destination.getLabel());

            // Back button shown:
            actionBar.setDisplayHomeAsUpEnabled(destinationId == R.id.detailFragment);
        }

        // BottomNavigation visible/hidden:
        binding.bottomNavigation.setVisibility(
                (destination.getId() == R.id.detailFragment)
                        ? View.GONE
                        : View.VISIBLE
        );
    }


    /**
     * Called when the user pushes the Up button in the ActionBar.
     * This method delegates the user's "Up requirement" to the NavController.
     *
     * @return True if navigation back was successful; False otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        return
                navController.navigateUp()
                        || super.onSupportNavigateUp();
    }


    /**
     * Returns the list of caught Pokémon.
     *
     * @return List of caught Pokémon.
     */
    public List<Pokemon> giveCaughtPokemon() {
        return caughtPokemon;
    }


    /**
     * Returns the list of Pokémon from the Pokédex.
     *
     * @return List of Pokémon from the Pokédex.
     */
    public List<PokemonId> givePokedex() {
        return pokedex;
    }


    /**
     * Refreshes the Pokédex by downloading Pokémon data up to the specified limit.
     *
     * @param limit The maximum number of Pokémon to fetch.
     */
    void refreshPokedex(int limit) {

        // Launching Pokédex download from API:
        ApiPokemon.downloadPokedex(POKEDEX_OFFSET, limit).addOnCompleteListener(taskDownloadPokedex -> {

            if (taskDownloadPokedex.isSuccessful() && taskDownloadPokedex.getResult() != null) {

                // Updating local version of the Pokédex:
                pokedex = taskDownloadPokedex.getResult();
                pokedexLimit = limit;

                // Indicating which Pokémon in the Pokédex have been previously caught:
                lockCaughtPokemon(caughtPokemon);

                Toast.makeText(this, R.string.pokedex_updated, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, R.string.pokedex_unavailable, Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * Launches the caught Pokémon detail screen by passing the Pokémon's information.
     * This method bundles the Pokémon's details into a 'Bundle' and navigates to the DetailFragment.
     *
     * @param pokemon The Pokémon whose details are to be displayed in the Pokémon detail screen.
     */
    public void launchPokemonDetail(Pokemon pokemon) {
        // Encapsulating the pokemon's data in a Bundle object:
        Bundle bundle = new Bundle();
        bundle.putString("name", pokemon.getName());
        bundle.putInt("index", pokemon.getIndex());
        bundle.putString("picture", pokemon.getPicture());
        bundle.putString("type", LocalizationTools.localizeTypes(pokemon.getType()));
        bundle.putString("weight", LocalizationTools.localizateWeight(pokemon.getWeight()));
        bundle.putString("height", LocalizationTools.localizateHeight(pokemon.getHeight()));
        bundle.putBoolean("release_allowed", releaseAllowed);

        // Navigating onto the DetailFragment:
        navController.navigate(R.id.detailFragment, bundle);
    }


    /**
     * Catches a Pokémon by storing its information in the database.
     *
     * @param pokemonId The ID of the Pokémon to catch.
     */
    public void catchPokemon(PokemonId pokemonId) {

        // 0. Avoiding to catch null Pokémon nor re-catch previously caught Pokemon:
        if ((pokemonId == null) || (pokemonId instanceof Pokemon))
            return;

        // 1. Getting the Pokémon info from the API:
        Task<Pokemon> downloadPokemon = ApiPokemon.catchPokemon(pokemonId);

        downloadPokemon.addOnCompleteListener(taskCatchPokemon -> {
            if (taskCatchPokemon.isSuccessful() && taskCatchPokemon.getResult() != null) {
                Pokemon pokemon = taskCatchPokemon.getResult();

                // 2. Storing the Pokemon info in the Database:
                Task<Boolean> storePokemon = Database.storePokemon(user, pokemon);
                storePokemon.addOnCompleteListener(taskStorePokemon -> {
                    if (taskStorePokemon.isSuccessful() && taskStorePokemon.getResult()) {

                        // 3. Adding the Pokemon to the Caught Pokemon list:
                        caughtPokemon.add(pokemon);
                        Collections.sort(caughtPokemon);

                        // 4. Locking the Pokémon in the Pokédex:
                        lockCaughtPokemon(pokemon);

                        // 5. Launching Pokémon detail:
                        launchPokemonDetail(pokemon);
                        Toast.makeText(this, pokemonId.getName() + " " + getString(R.string.has_been_caught), Toast.LENGTH_SHORT).show();
                    } else
                        // Storing in the Database was unavailable:
                        Toast.makeText(this, getString(R.string.uncaught_pokemon) + pokemonId.getName(), Toast.LENGTH_SHORT).show();
                });
            } else
                // Pokemon full info unavailable:
                Toast.makeText(this, getString(R.string.uncaught_pokemon) + pokemonId.getName(), Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * Locks caught Pokémon into the Pokédex by replacing their entries.
     *
     * @param caughtPokemon The list of caught Pokémon.
     */
    private void lockCaughtPokemon(List<Pokemon> caughtPokemon) {
        for (Pokemon pokemon : caughtPokemon)
            if (pokedex.contains(pokemon))
                pokedex.set(pokedex.indexOf(pokemon), pokemon);
    }


    /**
     * Locks a caught Pokémon into the Pokédex by replacing its entry.
     *
     * @param pokemon The caught Pokémon.
     */
    private void lockCaughtPokemon(Pokemon pokemon) {
        if (pokedex.contains(pokemon))
            pokedex.set(pokedex.indexOf(pokemon), pokemon);
    }


    /**
     * Allows or denies releasing Pokémon.
     *
     * @param allow True if releasing Pokémon is allowed, false otherwise.
     */
    void allowRelease(boolean allow) {
        releaseAllowed = allow;
    }


    /**
     * Checks if Pokémon release is allowed.
     *
     * @return True if Pokémon release is allowed, false otherwise.
     */
    public boolean isReleaseAllowed() {
        return releaseAllowed;
    }


    /**
     * Releases a Pokémon by removing it from the database and updating the Pokédex.
     *
     * @param pokemon            The Pokémon to release.
     * @param onCompleteListener The listener to notify when the operation is complete.
     * @noinspection SuspiciousMethodCalls
     */
    public void releasePokemon(PokemonId pokemon, OnCompleteListener<Void> onCompleteListener) {

        // 0. Masking the delete option:
        if (!releaseAllowed)
            return;

        // 1. Deleting the Pokémon info in the database:
        Task<Boolean> dropPokemon = Database.dropPokemon(user, pokemon);
        dropPokemon.addOnCompleteListener(taskDropPokemon -> {

            // Pokémon deleted in Database:
            if (taskDropPokemon.isSuccessful() && taskDropPokemon.getResult()) {

                // 2. Deleting the Pokémon from the Caught Pokémon list:
                caughtPokemon.remove(pokemon);

                // 3. Setting the Pokémon as free in the Pokédex:
                if (pokedex.contains(pokemon))
                    pokedex.set(pokedex.indexOf(pokemon),
                            new PokemonId(pokemon.getName(), pokemon.getIndex()));

                // 4. Calling back the Pokédex fragment:
                onCompleteListener.onComplete(Tasks.forResult(null));
                Toast.makeText(this, String.format("%s %s", pokemon.getName(), getString(R.string.released)), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, String.format("%s %s", getString(R.string.release_failed), pokemon.getName()), Toast.LENGTH_SHORT).show();
                onCompleteListener.onComplete(Tasks.forCanceled());
            }

        });
    }


    /**
     * Refreshes certain UI elements to reflect language changes.
     */
    void syncUILanguage() {
        // Refreshing the SettingsFragment layout (reentering):
        navController.navigate(R.id.settingsFragment);

        // Refreshing the BottomNavigationView menu titles:
        binding.bottomNavigation.getMenu().getItem(0).setTitle(R.string.my_pokemon);
        binding.bottomNavigation.getMenu().getItem(1).setTitle(R.string.pokedex);
        binding.bottomNavigation.getMenu().getItem(2).setTitle(R.string.settings);
    }
}

