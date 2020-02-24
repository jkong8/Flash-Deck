package student.gsu.edu.flashdeck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudyActivity extends AppCompatActivity {

    //COMPONENTS
    TextView header;
    TextView topic;
    TextView answer;

    private Button back;
    private Button next;

    //DATA AND VARIABLES
    private Deck selectedDeck;
    private int currentIndex;
    private Boolean answerShown;
    //EXTRAS
    public static final String SELECTED_DECK = "selectedDeck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        currentIndex = 0;
        answerShown=false;

        getExtras();


        if(selectedDeck.getFlashCards().size()==0){
            header = (TextView) findViewById(R.id.study_textViewHeader);
            header.setText("No Cards in Deck");
        }else{

            setTextViews();
            answer.setVisibility(View.INVISIBLE);
            setButtons();
            setHeader();
            setContent(currentIndex);
        }

    }

    public void getExtras(){
        Intent intent = this.getIntent();
        selectedDeck = intent.getParcelableExtra(SELECTED_DECK);
    }

    public void setTextViews(){
        header = (TextView) findViewById(R.id.study_textViewHeader);
        topic = (TextView) findViewById(R.id.study_textViewTopicText);
        answer = (TextView) findViewById(R.id.study_textViewAnswerText);
    }

    public void setButtons(){
        back = (Button) findViewById(R.id.study_buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPress();
            }
        });
        next = (Button) findViewById(R.id.study_buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPress();

            }
        });
    }

    public void backPress(){
        if(currentIndex > 0){
            currentIndex--;
            setContent(currentIndex);
            hideAnswer();
            answerShown = false;
        }

    }

    public void nextPress(){
        if(answerShown){
            setContent(currentIndex);
            next.setText("View Answer");
            hideAnswer();
            answerShown=false;
        }else{
            next.setText("Next Card");
            showAnswer();
            answerShown=true;
            currentIndex++;
        }
    }

    public void setHeader(){
        String text = "Card #"+(currentIndex+1)+"/"+selectedDeck.getFlashCards().size();
        header.setText(text);
    }

    public void setContent(int index){
        if(currentIndex > selectedDeck.getFlashCards().size()-1){
            currentIndex=0;
            setContent(currentIndex);
        }else{
            setHeader();
            topic.setText(selectedDeck.getFlashCards().get(index).getTopic());
            answer.setText(selectedDeck.getFlashCards().get(index).getAnswer());
        }
    }

    public void showAnswer(){
        answer.setVisibility(View.VISIBLE);
    }

    public void hideAnswer(){
        answer.setVisibility(View.INVISIBLE);
    }



}
