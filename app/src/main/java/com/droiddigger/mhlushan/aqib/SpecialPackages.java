package com.droiddigger.mhlushan.aqib;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class SpecialPackages extends AppCompatActivity {

    private Toolbar toolbar;

    EditText nameET, usernameET, addressET, emailET, contactET;
    LinearLayout layout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_packages);

        nameET = (EditText) findViewById(R.id.spcl_pkg_name);
        usernameET = (EditText) findViewById(R.id.usernamereg);
        addressET = (EditText) findViewById(R.id.addr);
        emailET = (EditText) findViewById(R.id.email);
        contactET = (EditText) findViewById(R.id.contact);

        layout=(LinearLayout)findViewById(R.id.container);
        progressBar= (ProgressBar) findViewById(R.id.progress);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void register(View view) {
        String nameTxt, usernameTxt, addressTxt, emailTxt, contactTxt;
        nameTxt = nameET.getText().toString();
        usernameTxt = usernameET.getText().toString();
        addressTxt = addressET.getText().toString();
        emailTxt = emailET.getText().toString();
        contactTxt = contactET.getText().toString();
        new Register(this).execute(nameTxt, usernameTxt, addressTxt, emailTxt, contactTxt);
    }

    public class Register extends AsyncTask<String, Void, String> {
        Context ctx;

        public Register(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            layout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "https://irrepealable-substi.000webhostapp.com/special_package.php";
            String name, username, address, email, contact;
            name = params[0];
            username = params[1];
            address = params[2];
            email = params[3];
            contact = params[4];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data =
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                                URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                                URLEncoder.encode("addr", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +
                                URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                                URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8");
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
            layout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            if(result.equals("Registered")){
                startActivity(new Intent(ctx,MainActivity.class));
            }
        }
    }
}
