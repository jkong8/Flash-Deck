package student.gsu.edu.flashdeck;

import android.os.Parcel;
import android.os.Parcelable;

public class FlashCard implements Parcelable {
    private String topic;
    private String answer;
    private int cardNumber;

    public FlashCard(String topic, String answer,int cardNumber){
        this.topic=topic;
        this.answer=answer;
        this.cardNumber=cardNumber;
    }

    protected FlashCard(Parcel in) {
        topic = in.readString();
        answer = in.readString();
        cardNumber = in.readInt();
    }

    public static final Creator<FlashCard> CREATOR = new Creator<FlashCard>() {
        @Override
        public FlashCard createFromParcel(Parcel in) {
            return new FlashCard(in);
        }

        @Override
        public FlashCard[] newArray(int size) {
            return new FlashCard[size];
        }
    };

    public String getTopic(){
        return topic;
    }
    public String getAnswer(){
        return answer;
    }
    public String getCardNumber(){
        String num = ""+cardNumber;
        return num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(topic);
        parcel.writeString(answer);
        parcel.writeInt(cardNumber);
    }

    public void setCardNumber(int num){
        this.cardNumber = num;
    }
}
