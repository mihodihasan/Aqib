package com.droiddigger.mhlushan.aqib;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

public class AdminReplyChooser extends AppCompatActivity {

    RecyclerView recyclerView;
    UserListAdapter adapter;
    List<Users> usersList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reply_chooser);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        recyclerView= (RecyclerView) findViewById(R.id.usersRecycler);
        usersList=new ArrayList<>();
        adapter=new UserListAdapter(this,usersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        new LoadUsers(this).execute();
    }

    private void populateData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("users");
            int count = 0;
            String name, username, address,email,contact;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                name = jo.getString("name");
                username = jo.getString("username");
                address = jo.getString("address");
                email = jo.getString("email");
                contact = jo.getString("contact");

                Users users=new Users(name,username,address,email,contact);
                usersList.add(users);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    class UserListAdapter extends RecyclerView.Adapter<UserListVH>{
        Context context;
        List<Users> list;

        public UserListAdapter(Context context, List<Users> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public UserListVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UserListVH(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
        }

        @Override
        public void onBindViewHolder(UserListVH holder, int position) {
            holder.textView.setText(list.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class UserListVH extends RecyclerView.ViewHolder{
        TextView textView;
        public UserListVH(View itemView) {
            super(itemView);

            textView=(TextView)itemView.findViewById(R.id.userTV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getBaseContext(),FeedbackActivity.class);
                    intent.putExtra("sender","admin");
                    intent.putExtra("receiver",usersList.get(getAdapterPosition()).getUsername());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    class Users{
        String name,username,address,email,contact;

        public Users(String name, String username, String address, String email, String contact) {
            this.name = name;
            this.username = username;
            this.address = address;
            this.email = email;
            this.contact = contact;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }
    }

    public class LoadUsers extends AsyncTask<String, Void, String> {
        Context ctx;
        String jsonUrl;
        String jasonString;
        public LoadUsers(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPostExecute(String result) {
            populateData(result);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            jsonUrl = "https://irrepealable-substi.000webhostapp.com/show_users.php";
            recyclerView.setVisibility(View.GONE);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
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


}
