package com.droiddigger.mhlushan.aqib;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.droiddigger.mhlushan.aqib.R.drawable.a2;

public class SpecialPackagePrev extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Offers>  list;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_package_prev);

        recyclerView = (RecyclerView) findViewById(R.id.specialPKGRecycler);
        list=new ArrayList<>();
        list.add(new Offers(R.drawable.a1,"Offer1"));//, a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a7,R.drawable.a8);
        list.add(new Offers(R.drawable.a2,"Offer1"));
        list.add(new Offers(R.drawable.a3,"Offer1"));
        list.add(new Offers(R.drawable.a4,"Offer1"));
        list.add(new Offers(R.drawable.a5,"Offer1"));
        list.add(new Offers(R.drawable.a6,"Offer1"));
        list.add(new Offers(R.drawable.a7,"Offer1"));
        list.add(new Offers(R.drawable.a8,"Offer1"));


        adapter=new RecyclerAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        ArrayList<Offers> list = new ArrayList<>();
        Context context;

        public RecyclerAdapter(ArrayList<Offers> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.list_row, parent, false
            );
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.imageView.setImageDrawable(getDrawable(list.get(position).getImg()));
            holder.textView.setText(list.get(position).getOffer());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.row_img);
            textView=(TextView)itemView.findViewById(R.id.offerText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getBaseContext(),SpecialPackages.class);
                    startActivity(intent);
                }
            });
        }
    }
}
