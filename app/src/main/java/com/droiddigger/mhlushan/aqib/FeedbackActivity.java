package com.droiddigger.mhlushan.aqib;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FeedbackActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Feedback> feedbacks;
    FeedbackRecyclerAdapter adapter;
    EditText feedbackSendingET;
    Toolbar toolbar;
    String sender, receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        sender = getIntent().getStringExtra("sender");
        receiver = getIntent().getStringExtra("receiver");

        recyclerView = (RecyclerView) findViewById(R.id.feedbackRecycler);
        feedbackSendingET = (EditText) findViewById(R.id.feedbackSendingET);
        feedbacks = new ArrayList<>();

        adapter = new FeedbackRecyclerAdapter(this, feedbacks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");

        refreshFeedbacks();

    }

    public void refreshFeedbacks() {
        feedbacks.clear();
        new LoadFeedbacks(getApplication()).execute();
//        try {
//            Thread.sleep(2000);
//        } catch (Exception e) {
//
//        }
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(feedbacks.size() - 1);
//        refreshFeedbacks();
    }

    public void sendTxt(View view) {
//        feedbacks.add(new Feedback(feedbackSendingET.getText().toString(),sender,receiver));
        adapter.notifyDataSetChanged();

        new SendFeedback(this).execute(feedbackSendingET.getText().toString(), sender, receiver);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(feedbacks.size() - 1);
        Toast.makeText(this, feedbackSendingET.getText().toString(), Toast.LENGTH_LONG).show();
        feedbackSendingET.setText("");
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

    public class LoadFeedbacks extends AsyncTask<String, Void, String> {
        Context ctx;
        String jsonUrl;
        String jasonString = "";

        public LoadFeedbacks(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("feedbacks");

                int count = 0;
                String feedback, sender, receiver;//,email,contact;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    feedback = jo.getString("feedback");
                    sender = jo.getString("sender");
                    receiver = jo.getString("receiver");
                    feedbacks.add(new Feedback(feedback, sender, receiver));
                    count++;
                }
            } catch (JSONException e) {
            }
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(feedbacks.size() - 1);
        }


        @Override
        protected String doInBackground(String... params) {
            jsonUrl = "https://irrepealable-substi.000webhostapp.com/read_feedback.php?sender=" + sender + "&&receiver=" + receiver;
            try {
                URL url = new URL(jsonUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((jasonString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(jasonString + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String r = stringBuilder.toString().trim();
                return r;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Unsuccessful";
        }
    }

    public class SendFeedback extends AsyncTask<String, Void, String> {
        Context ctx;

        public SendFeedback(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "https://irrepealable-substi.000webhostapp.com/insert_feedback.php";
            String feedback, sender, receiver;//, email, contact;
            feedback = params[0];
            sender = params[1];
            receiver = params[2];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data =
                        URLEncoder.encode("feedback", "UTF-8") + "=" + URLEncoder.encode(feedback, "UTF-8") + "&" +
                                URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(sender, "UTF-8") + "&" +
                                URLEncoder.encode("receiver", "UTF-8") + "=" + URLEncoder.encode(receiver, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                String response = "";
                String line = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                IS.close();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Unsuccessful";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sync) {
            refreshFeedbacks();
        }
        return super.onOptionsItemSelected(item);
    }
}
