package com.iesaguadulce.lopez_salazar_mario_pmdm03.model.database;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.Pokemon;
import com.iesaguadulce.lopez_salazar_mario_pmdm03.model.PokemonId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Provides methods to interact with the Firebase Firestore database for managing Trainers (APP users) and Pokémon data.
 * Some examples of the collections supported: <ul>
 * <li>  trainers/ [userUID] / pokemon / [RANDOM_DOC.REFERENCE] / { name="Metapod", index=7, picture="https://...", type=["bug"], weight=99, height=7 }  </li>
 * <li>  types / ES / types-ES / [RANDOM_DOC.REFERENCE] / { default="rock", ES="roca" }   </li>
 * </ul>
 *
 * @author Mario López Salazar
 */
public abstract class Database {


    /**
     * Name of the root collection for storing trainers.
     */
    public static final String COLLECTION_TRAINERS = "trainers";

    /**
     * Name of the collection for storing the caught Pokémon of a trainer.
     */
    public static final String COLLECTION_CAUGHTPOKEMON = "pokemon";

    /**
     * Name of the root collection for storing the localized names of the types of Pokémon.
     */
    public static final String COLLECTION_TYPES = "types";

    /**
     * Firestore database instance for accessing and managing data.
     */
    @SuppressLint("StaticFieldLeak")
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     * Retrieves the list of Pokémon caught by a specific trainer.
     *
     * @param user The authenticated Firebase user (null allowed).
     * @return A task that resolves to a list of Pokemon objects,
     * or an empty list if the user has no Pokémon yet, or null if an error occurs.
     * This allows the caller to know when the List of Pokémon is ready for use.
     */
    public static Task<List<Pokemon>> getCaughtPokemon(FirebaseUser user) {

        TaskCompletionSource<List<Pokemon>> task = new TaskCompletionSource<>();

        // Managing null user:
        if (user == null) {
            task.setResult(null);
            return task.getTask();
        }

        // Checking if the trainer is known in the database:
        String userUID = user.getUid();
        DocumentReference referenceTrainer = db.collection(COLLECTION_TRAINERS).document(userUID);
        referenceTrainer.get().addOnCompleteListener(taskUserExists -> {

            // Trainer is known in the database:
            if (taskUserExists.isSuccessful() && taskUserExists.getResult() != null)

                // Getting his/her Pokemon collection:
                referenceTrainer.collection(COLLECTION_CAUGHTPOKEMON).get().addOnCompleteListener(taskGetPokemon -> {

                    // Collection recovered:
                    if (taskGetPokemon.isSuccessful() && taskGetPokemon.getResult() != null) {
                        List<Pokemon> list = new ArrayList<>();

                        // Extracting all the Pokémon of the trainer:
                        for (DocumentSnapshot pokemon : taskGetPokemon.getResult())
                            list.add(pokemon.toObject(Pokemon.class));

                        // Sorting Pokémon by index:
                        Collections.sort(list);
                        task.setResult(list);
                    }

                    // Collection unavailable:
                    else
                        task.setResult(null);
                });

            else if (taskUserExists.isSuccessful())
                // Trainer is unknown in the database (had no Pokemon yet):
                task.setResult(new ArrayList<>());

            else
                // Trainer query error:
                task.setResult(null);
        });

        return task.getTask();
    }


    /**
     * Stores a Pokémon in the database for a specific user.
     *
     * @param user    The authenticated Firebase user.
     * @param pokemon The Pokemon object to store.
     * @return A task that resolves to a boolean value that indicates if the storing has been successful.
     * This allows the caller to know when the storing call is done.
     */
    public static Task<Boolean> storePokemon(@NonNull FirebaseUser user, @NonNull Pokemon pokemon) {

        TaskCompletionSource<Boolean> task = new TaskCompletionSource<>();

        // Getting trainer's Pokémon collection reference:
        String userUID = user.getUid();
        CollectionReference reference = db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAUGHTPOKEMON);

        // Requiring add Pokémon to that collection:
        reference.add(pokemon).addOnCompleteListener(taskAddPokemon ->

                // Notifying the caller if the store has been successful:
                task.setResult(taskAddPokemon.isSuccessful())
        );

        return task.getTask();
    }


    /**
     * Deletes a Pokémon from the database for a specific user.
     *
     * @param user    The authenticated Firebase user.
     * @param pokemon The PokemonId object representing the Pokémon to delete.
     * @return A task that resolves to a boolean value that indicates if the deletion has been successful.
     * This allows the caller to know when the delete call is done.
     */
    public static Task<Boolean> dropPokemon(@NonNull FirebaseUser user, @NonNull PokemonId pokemon) {

        TaskCompletionSource<Boolean> task = new TaskCompletionSource<>();

        // Getting Pokémon's reference from the trainer's Pokémon collection:
        String userUID = user.getUid();
        Query query = db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAUGHTPOKEMON)
                .whereEqualTo("index", pokemon.getIndex());

        // Retrieving the Firestore reference to that Pokemon:
        query.get().addOnCompleteListener(taskRetrieveReference -> {

            // Got Pokemon reference:
            if (taskRetrieveReference.isSuccessful() && taskRetrieveReference.getResult() != null)

                // Deleting the Pokemon in Firestore:
                taskRetrieveReference.getResult().getDocuments().get(0).getReference()
                        .delete().addOnCompleteListener(taskDelete ->
                                // Notifying the caller if the delete has been successful:
                                task.setResult(taskDelete.isSuccessful())
                        );
            else
                // Didn't get Pokemon reference:
                task.setResult(false);
        });

        return task.getTask();
    }


    /**
     * Deletes a trainer and his/her caught Pokémon from the database.
     *
     * @param user The authenticated Firebase user.
     * @return A task that resolves to a boolean value that indicates if the deletion has been successful.
     * This allows the caller to know when the delete call is done.
     */
    public static Task<Boolean> dropUser(@NonNull FirebaseUser user) {

        TaskCompletionSource<Boolean> task = new TaskCompletionSource<>();

        // Getting trainer's Pokémon collection reference:
        String userUID = user.getUid();
        Query query = db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAUGHTPOKEMON);

        // Retrieving the Firestore reference to his/her Pokémon collection:
        query.get().addOnCompleteListener(taskRetrieveReference -> {

            // Got collection reference:
            if (taskRetrieveReference.isSuccessful()) {

                // Launching deletion of all trainer's Pokémon:
                List<Task<Void>> deleteTasks = new ArrayList<>();
                for (DocumentSnapshot pokemon : taskRetrieveReference.getResult().getDocuments())
                    deleteTasks.add(pokemon.getReference().delete());

                // Waiting for all the delete tasks to be completed:
                Tasks.whenAllComplete(deleteTasks).addOnCompleteListener(deleteAllTask -> {

                    // Checking if all trainer's Pokémon has been deleted:
                    boolean allSuccessfull = true;
                    for (Task<Void> t : deleteTasks)
                        allSuccessfull &= t.isSuccessful();

                    // Once all Pokémon has been deleted, the trainer's document is automatically deleted on Firestore.
                    task.setResult(allSuccessfull);
                });
            } else
                // Didn't get Pokemon collection reference:
                task.setResult(false);
        });

        return task.getTask();
    }


    /**
     * Retrieves localized Pokémon type names for a specific language.
     * This method gets from Firebase a dictionary for types translation.
     *
     * @param language The capitalized language code (e.g., "ES").
     * @return A task that resolves to a map where the key is the default type name and the value is the localized type name,
     * or null if an error occurs. This allows the caller to know when the getting call is done.
     */
    public static Task<Map<String, String>> getLocalizedTypes(@NonNull String language) {

        TaskCompletionSource<Map<String, String>> task = new TaskCompletionSource<>();

        // Getting types dictionary collection reference:
        // example: types / ES / types-ES / [RANDOM_DOC.REFERENCE] / { default="rock", ES="roca"
        CollectionReference collection = db
                .collection(COLLECTION_TYPES)
                .document(language)
                .collection(COLLECTION_TYPES + "-" + language);

        // Retrieving the dictionary from Firestore:
        collection.get().addOnCompleteListener(taskGetTypesCollection -> {

            // When dictionary is available for the specified language code:
            if (taskGetTypesCollection.isSuccessful() && taskGetTypesCollection.getResult() != null) {

                Map<String, String> dictionary = new HashMap<>();

                // Getting each type:
                for (QueryDocumentSnapshot type : taskGetTypesCollection.getResult()) {
                    Object key = type.get("default");
                    Object value = type.get(language);
                    if (key != null && value != null)
                        dictionary.put(key.toString(), value.toString());
                }
                task.setResult(dictionary);

            } else
                // When dictionary is NOT available:
                task.setResult(null);
        });

        return task.getTask();
    }

}
