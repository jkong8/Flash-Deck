package student.gsu.edu.flashdeck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    //COMPONENTS
    TextView viewTextView;
    RecyclerView recyclerView;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager rLa;

    Button edit;
    Button study;
    Button save;

    //Data and Variables
    private Deck selectedDeck;
    private int index;
    //private ArrayList<FlashCard> cards;

    //EXTRAS
    public static final String SELECTED_DECK = "selectedDeck";
    public static final String INDEX = "index";

    //USED TO SAVE DATA?
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        loadData();
        saveData();
        getExtras();
        setButtons();
        setViewTextView();
        setRecyclerView();

        System.out.println(selectedDeck);
    }

    //GETS THE EXTRAS FROM MAIN ACTIVITY
        private void getExtras(){
            Intent intent = this.getIntent();
            selectedDeck = intent.getParcelableExtra(SELECTED_DECK);
            index = intent.getIntExtra(INDEX,0);

            System.out.println(".");
            System.out.println("Selected Deck: "+selectedDeck.getSubject());
            System.out.println("Index: "+index);
        }

        public void setButtons(){
            //EDIT BUTTON
            edit = (Button) findViewById(R.id.view_buttonEdit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startEditActivity();
                }
            });

            //SAVE BUTTON
            save = (Button) findViewById(R.id.view_buttonSave);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            //STUDY BUTTON
            study = (Button) findViewById(R.id.view_buttonStudy);
            study.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startStudyActivity();
                }
            });
        }


        public void setViewTextView(){
            viewTextView = findViewById(R.id.view_textView);
            viewTextView.setText(selectedDeck.getSubject());
        }

        public void setRecyclerView(){
            recyclerView = (RecyclerView) findViewById(R.id.view_recyclerView);
            rLa = new LinearLayoutManager(this);

            //THIS WORKS
            /*ArrayList<FlashCard> cards = new ArrayList<>();
            cards.add(new FlashCard("doko","where",1));*/
            rAdapter = new myRecyclerAdapter(selectedDeck.getFlashCards());
            recyclerView.setHasFixedSize(true);



            recyclerView.setLayoutManager(rLa);
            recyclerView.setAdapter(rAdapter);
        }

        public void startEditActivity(){
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra(SELECTED_DECK, this.selectedDeck);
            intent.putExtra(INDEX,index);
            startActivity(intent);
        }

        public void startStudyActivity(){
            Intent intent = new Intent(this, StudyActivity.class);
            intent.putExtra(SELECTED_DECK, this.selectedDeck);
            intent.putExtra(INDEX,index);
            startActivity(intent);
        }

        public void saveData(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(SELECTED_DECK,null);
            Type type = new TypeToken<ArrayList<Deck>>(){}.getType();
            selectedDeck = gson.fromJson(json, type);

        }

        public void loadData(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(SELECTED_DECK,null);
            Type type = new TypeToken<ArrayList<Deck>>(){}.getType();
            selectedDeck = gson.fromJson(json, type);
        }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        System.out.println("You pressed the back button");
        System.out.println("Index sent back: "+ index);
        Intent intent = new Intent(this, MainActivity.class);
        //EXTRAS FOR MAIN ACTIVITY
        intent.putExtra(SELECTED_DECK,selectedDeck);
        intent.putExtra(INDEX,index);

        startActivity(intent);
    }

}
