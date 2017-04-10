package com.droiddigger.mhlushan.aqib;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Hotel extends AppCompatActivity {

    AutoCompleteTextView hotelNameET, hotelCountryET, hotelCityET, hotelDateET;
    ProgressBar hotelProgressBar;
    private Toolbar toolbar;
    RecyclerView hotelRecycler;
    HotelRecyclerAdaper adaper;
    List<HotelData> hotelDataList;
    List<String> countryList, cityList;
    List<HotelDataForRecycler> recyclerList;
    private ArrayAdapter<String> countryAdapter, cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        recyclerList = new ArrayList<>();
        adaper = new HotelRecyclerAdaper(recyclerList, this);
        hotelRecycler = (RecyclerView) findViewById(R.id.hotelRecycler);
        hotelRecycler.setLayoutManager(new LinearLayoutManager(this));
        hotelDataList = new ArrayList<>();
        hotelProgressBar = (ProgressBar) findViewById(R.id.hotelProgress);

        new LoadHotelData(this).execute();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hotelRecycler.setAdapter(adaper);
        initializeAutocompleteEditTextFeature();

    }

    private void initializeAutocompleteEditTextFeature() {
        hotelNameET = (AutoCompleteTextView) findViewById(R.id.hotelName);
        hotelCityET = (AutoCompleteTextView) findViewById(R.id.hotelCity);
        hotelCountryET = (AutoCompleteTextView) findViewById(R.id.hotelCountry);
        hotelDateET = (AutoCompleteTextView) findViewById(R.id.hotelDate);

        countryList = new ArrayList<>();
        cityList = new ArrayList<>();


        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                countryList);
        hotelCountryET.setAdapter(countryAdapter);

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                cityList);
        hotelCityET.setAdapter(cityAdapter);

        hotelCountryET.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                prepareCity(hotelCountryET.getText().toString());
            }
        });

        hotelCityET.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                prepareHotelListData(hotelCountryET.getText().toString(),hotelCityET.getText().toString());
            }
        });
    }

    void prepareCountry() {
        for (int i = 0; i < hotelDataList.size(); i++) {
            if(!countryList.contains(hotelDataList.get(i).getHotelCountry())){
                countryList.add(hotelDataList.get(i).getHotelCountry());
            }
        }
        countryAdapter.notifyDataSetChanged();
    }

    private void prepareHotelListData(String country, String city) {
        recyclerList.clear();
        for (int i = 0; i < hotelDataList.size(); i++) {
            if (hotelDataList.get(i).getHotelCountry().equalsIgnoreCase(country) &&
                    hotelDataList.get(i).getHotelCity().equalsIgnoreCase(city)) {
                recyclerList.add(new HotelDataForRecycler(hotelDataList.get(i).getHotelName(),
                        hotelDataList.get(i).getStatus()));
            }
        }
        Log.d("size", "hotel" + recyclerList.size());
        adaper.notifyDataSetChanged();
    }

    private void prepareCity(String country) {
        cityList.clear();
        for (int i = 0; i < hotelDataList.size(); i++) {
            if (hotelDataList.get(i).getHotelCountry().equalsIgnoreCase(country) &&
                    !cityList.contains(hotelDataList.get(i).getHotelCity())) {
                cityList.add(hotelDataList.get(i).getHotelCity());
            }
        }
        Log.d("siize", "city" + cityList.size());
        cityAdapter.notifyDataSetChanged();
    }

    public class LoadHotelData extends AsyncTask<String, Void, String> {
        Context ctx;
        String jsonUrl;
        String jasonString = "";

        public LoadHotelData(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hotelProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            jsonUrl = "https://irrepealable-substi.000webhostapp.com/hotel.php";
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

        @Override
        protected void onPostExecute(String result) {
            hotelProgressBar.setVisibility(View.GONE);
            hotelDataList.clear();
            recyclerList.clear();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("hotels");

                int count = 0;
                String hotel_name, hotel_country, hotel_city, hotel_date, hotel_status;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    hotel_name = jo.getString("hotel_name");
                    hotel_country = jo.getString("hotel_country");
                    hotel_city = jo.getString("hotel_city");
                    hotel_date = jo.getString("hotel_date");
                    hotel_status = jo.getString("hotel_status");
                    hotelDataList.add(new HotelData(hotel_name, hotel_country, hotel_city, hotel_date, hotel_status));
                    recyclerList.add(new HotelDataForRecycler(hotel_name, hotel_status));
                    count++;
                }
            } catch (JSONException e) {
            }
            Log.d("size", "hotelDataList" + hotelDataList.size());
            adaper.notifyDataSetChanged();
            prepareCountry();
        }
    }
    class HotelData {
        String hotelName, hotelCountry, hotelCity, date, status;

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getHotelCountry() {
            return hotelCountry;
        }

        public void setHotelCountry(String hotelCountry) {
            this.hotelCountry = hotelCountry;
        }

        public String getHotelCity() {
            return hotelCity;
        }

        public void setHotelCity(String hotelCity) {
            this.hotelCity = hotelCity;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public HotelData(String hotelName, String hotelCountry, String hotelCity, String date, String status) {
            this.hotelName = hotelName;
            this.hotelCountry = hotelCountry;
            this.hotelCity = hotelCity;
            this.date = date;
            this.status = status;


        }
    }

    class HotelRecyclerAdaper extends RecyclerView.Adapter<HRViewHolder> {

        List<HotelDataForRecycler> hotelList;
        Context context;

        public HotelRecyclerAdaper(List<HotelDataForRecycler> hotelList, Context context) {
            this.hotelList = hotelList;
            this.context = context;
        }

        @Override
        public HRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HRViewHolder(LayoutInflater.from(context).inflate(R.layout.hotel_status_row, parent, false));
        }

        @Override
        public void onBindViewHolder(HRViewHolder holder, int position) {
            holder.hotelName.setText(hotelList.get(position).getName());
            holder.hotelStatus.setText(hotelList.get(position).getStatus());
            if (hotelList.get(position).getStatus().startsWith("A")||
                    hotelList.get(position).getStatus().startsWith("a")) {
                holder.hotelStatus.setTextColor(Color.GREEN);
            } else {
                holder.hotelStatus.setTextColor(Color.RED);
            }
        }

        @Override
        public int getItemCount() {
            return hotelList.size();
        }
    }

    class HRViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelStatus;

        public HRViewHolder(View itemView) {
            super(itemView);

            hotelName = (TextView) itemView.findViewById(R.id.hotel_name_row);
            hotelStatus = (TextView) itemView.findViewById(R.id.hotel_status_row);
        }
    }

    class HotelDataForRecycler {
        String name, status;

        public HotelDataForRecycler(String name, String status) {
            this.name = name;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
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
}
