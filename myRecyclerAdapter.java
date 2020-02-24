package student.gsu.edu.flashdeck;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class myRecyclerAdapter extends RecyclerView.Adapter<myRecyclerAdapter.ExampleViewHolder> {
    private ArrayList<FlashCard> mFlashCards;
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext() ).inflate(R.layout.card, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    public myRecyclerAdapter(ArrayList<FlashCard> flashCards){
        mFlashCards = flashCards;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        FlashCard currentItem = mFlashCards.get(i);
        exampleViewHolder.number.setText(currentItem.getCardNumber());
        exampleViewHolder.answer.setText(currentItem.getAnswer());
        exampleViewHolder.topic.setText(currentItem.getTopic());
    }

    @Override
    public int getItemCount() {
        return mFlashCards.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView number;
        public TextView topic;
        public TextView answer;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            number =  itemView.findViewById(R.id.cardNumber);
            topic = itemView.findViewById(R.id.topic);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}
