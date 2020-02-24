package student.gsu.edu.flashdeck;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Deck implements Parcelable {
    private String subject;
    private ArrayList<FlashCard> cards;

    public Deck(String subject){
        this.subject=subject;
        this.cards = new ArrayList<FlashCard>();
    }

    protected Deck(Parcel in) {
        subject = in.readString();
        cards = in.createTypedArrayList(FlashCard.CREATOR);
    }


    public String getSubject(){
        return this.subject;
    }

    public void add(FlashCard card){
        this.cards.add(card);
    }

    public void delete(int index){
        System.out.println("INDEX FOR DELETED CARD: "+index);
        cards.remove(index);
    }

    public void resetCardNubers(){
        for(int i = 0; i < cards.size();i++){
            cards.get(i).setCardNumber(i+1);
        }
    }

    public ArrayList<FlashCard> getFlashCards(){
        return this.cards;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subject);
        parcel.writeTypedList(cards);
    }


    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

    public String toString(){
        String deck = "";
        for(FlashCard c: cards){
            deck+=("#"+c.getCardNumber()+ " Topic: "+ c.getTopic() + " Answer: "+c.getAnswer()+"\n");
        }
        return deck;
    }
}
