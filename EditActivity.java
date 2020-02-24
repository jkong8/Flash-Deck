package student.gsu.edu.flashdeck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    //COMPONENTS

    EditText topic;
    EditText answer;
    EditText cardNumber;

    Button add;
    Button delete;

    //DATA AND VARIABLES
    private Deck selectedDeck;
    private int index;

    //EXTRAS
    public static final String SELECTED_DECK = "selectedDeck";
    public static final String INDEX = "index";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getExtras();
        setEditTexts();
        setButtons();
    }

    public void getExtras(){
        Intent intent = this.getIntent();
        selectedDeck = intent.getParcelableExtra(SELECTED_DECK);
        index = intent.getIntExtra(INDEX,0);
        System.out.println(".");
        System.out.println(selectedDeck.getSubject());
    }

    public void setEditTexts(){
        topic = (EditText) findViewById(R.id.edit_editTextTopic);
        answer = (EditText) findViewById(R.id.edit_editTextAnswer);
        cardNumber = (EditText) findViewById(R.id.edit_editTextNum);
    }

    public void setButtons(){
        add = findViewById(R.id.edit_buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        delete = findViewById(R.id.edit_buttonDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    public void add(){
        System.out.println("Add Button Pressed");
        if(topic.getText().toString().equals("")||answer.getText().toString().equals("")){
            Toast.makeText(this,"Input Topic and Answer",Toast.LENGTH_SHORT).show();
        }else{
            selectedDeck.add(new FlashCard(topic.getText().toString(), answer.getText().toString(),selectedDeck.getFlashCards().size()+1));
            Toast.makeText(this,"Card Added",Toast.LENGTH_SHORT).show();
            topic.setText("");
            answer.setText("");
        }
    }

    public void delete(){
        System.out.println(selectedDeck.getFlashCards().size());
        if(cardNumber.getText().toString().equals("")){

        }else {
            String input = cardNumber.getText().toString();
            int cardnumber = Integer.parseInt(input) - 1;
            if (cardnumber >= 0 && cardnumber < selectedDeck.getFlashCards().size()) {
                Toast.makeText(this, "Card Deleted", Toast.LENGTH_SHORT).show();
                selectedDeck.delete(cardnumber);
                selectedDeck.resetCardNubers();
                onBackPressed();
            } else {
                Toast.makeText(this, "Card " + (cardnumber + 1) + " does not exist", Toast.LENGTH_SHORT).show();
                cardNumber.setText("");
            }
        }
    }


    @Override
    public void onBackPressed() {
        //TEMPORARY
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(SELECTED_DECK,selectedDeck);
        intent.putExtra(INDEX,index);
        startActivity(intent);
    }
}
