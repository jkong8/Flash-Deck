package student.gsu.edu.flashdeck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    //Components
        ListView listView ;
        Button button;
        EditText editText;

    //Data and Variables
        //Main ArrayList used to access flashcards.
        ArrayList<Deck> decks;
        //ArrayList used to update the ListView.
        List<String> deckSubjects = new ArrayList<String>();
        //ArrayAdapter that uses 'deckSubjects' to input Subject Strings into the ListView.
        ArrayAdapter<String> arrayAdapter;

    //Used to Save Data?
        public static final String SHARED_PREFS = "sharedPrefs";
        public static final String DECKS = "decks";

    //EXTRAS
        public static final String SELECTED_DECK = "selectedDeck";
        public static final String INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadData();
        getExtras();
        //Initialize the component
            //LIST VIEW AND IT'S ON ITEM CLICK LISTENER
            listView = (ListView) findViewById(R.id.main_listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(decks.size()!=0){
                        System.out.println("List View Clicked "+i);
                        startViewActivity(i);
                    }

                }
            });
            //BUTTON AND IT'S ON CLICK LISTENER.
            button = (Button) findViewById(R.id.main_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    buttonPressed();
                }
            });

            //EDIT TEXT
            editText = (EditText) findViewById(R.id.editText);

         //This Method is called when you update the deckSubjects ArrayList which in turn, update the ListView.
            setListView();

         //Creates the arrayAdapter for the ListView and set the adapter for the ListView
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,deckSubjects);
            listView.setAdapter(arrayAdapter);

            //IF YOU WANNA CLEAR ALL LIST VIEWS THEN DO THIS AND OPEN CLOSE
            //clearData();
    }

    //Used to populate the list view.
        public void setListView(){
            //Clears everything first.
            deckSubjects.clear();
            //If deck is empty, Place empty message in ListView.
            if(decks.size()==0){
                deckSubjects.add("No Decks Created");
            }else{
                //Else, populate ListView with the subjects of all decks in deck ArrayList.
                for(Deck a:decks){
                    deckSubjects.add(a.getSubject());
                }
            }

            saveData();
        }

    //Button method
        public void buttonPressed(){

            //Makes the edit text appear you click the button for first time.
            if(button.getText().equals("New Deck")){
                editText.setVisibility(View.VISIBLE);
                button.setText(R.string.buttonName2);
            }else if(button.getText().equals("Create Deck")){
                //Checks if you inserted any text in the edit text. If so, add a new deck to deck ArrayList and update ListView.
                if(editText.getText().toString().equals("")){
                    editText.setHint("Please give a subject");
                }else{
                    String subject = editText.getText().toString();
                    decks.add(new Deck(subject));

                    setListView();

                    button.setText("New Deck");
                    editText.setVisibility(View.GONE); //Makes the editText disappear.
                    editText.setText(""); //Resets editText

                    //100% IMPORTANT IF YOU EVER WANT TO WORK WITH LIST VIEWS AGAIN!
                    //UPDATES LIST-VIEW ON SCREEN
                    arrayAdapter.notifyDataSetChanged();

                    printDecks(); //Makes sure new deck is created.
                }
            }
        }

    //Prints all decks.
        public void printDecks(){
            System.out.println();
            for(Deck a: decks){
                System.out.println(a.getSubject()+"\n");
            }
        }

    //Saves Changes to the decks ArrayList.
        public void saveData(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Gson gson = new Gson();
            String json = gson.toJson(decks);
            editor.putString(DECKS,json);
            editor.apply();
        }
    //Loads data from the decks ArrayList
        public void loadData(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(DECKS,null);
            Type type = new TypeToken<ArrayList<Deck>>(){}.getType();
            decks = gson.fromJson(json, type);

            if(decks == null){
                decks = new ArrayList<Deck>();
            }
        }

        public void clearData(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            decks.clear();
            saveData();
        }

        public void startViewActivity(int index){
            Intent intent = new Intent(this, ViewActivity.class);
            intent.putExtra(SELECTED_DECK,decks.get(index));
            intent.putExtra(INDEX,index);
            startActivity(intent);
        }

        public void getExtras(){
            Intent intent = this.getIntent();
            int eIndex = intent.getIntExtra(INDEX,0);
            Deck eSelectedDeck = intent.getParcelableExtra(SELECTED_DECK);

            if(eSelectedDeck != null){
                decks.set(eIndex, eSelectedDeck);
            }else{
                System.out.println("APP JUST STARTED");
            }

            System.out.println(eSelectedDeck);
            System.out.println(eIndex);
        }

}
