package com.droiddigger.mhlushan.aqib;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Feedback> feedbacks;
    FeedbackRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        recyclerView = (RecyclerView) findViewById(R.id.feedbackRecycler);
        feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Helo Brother", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefgHi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));
        feedbacks.add(new Feedback("zjcvnsidbjsbdbvbisdvbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjhksdvsdvbiduvdnvuabyuakauybieyuvbadubv7dyvukbvyefg", "Lushan", "Admin"));
        feedbacks.add(new Feedback("Hi", "Lushan", "Admin"));

        adapter = new FeedbackRecyclerAdapter(this, feedbacks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class FeedbackRecyclerAdapter extends RecyclerView.Adapter<FeedbackVH> {

        Context context;
        List<Feedback> feedbackList;

        public FeedbackRecyclerAdapter(Context context, List<Feedback> feedbackList) {
            this.context = context;
            this.feedbackList = (ArrayList) feedbackList;
        }

        @Override
        public FeedbackVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FeedbackVH(LayoutInflater.from(context).inflate(R.layout.feedback_list_row, parent, false));
        }

        @Override
        public void onBindViewHolder(FeedbackVH holder, int position) {
            holder.feedbackTV.setText(feedbackList.get(position).getFeedback());
            holder.senderTV.setText(feedbackList.get(position).getSender());
        }

        @Override
        public int getItemCount() {
            return feedbackList.size();
        }
    }

    class FeedbackVH extends RecyclerView.ViewHolder {
        TextView feedbackTV, senderTV;

        public FeedbackVH(View itemView) {
            super(itemView);
            feedbackTV = (TextView) itemView.findViewById(R.id.txt);
            senderTV = (TextView) itemView.findViewById(R.id.txtSender);
        }
    }
}
