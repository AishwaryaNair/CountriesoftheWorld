package com.example.aishwarya.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import android.widget.AdapterView.OnItemClickListener;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aishwarya on 4/1/16.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.Holder> {


    public static String TAG = CountryAdapter.class.getSimpleName();

    private List<Country> mCountries;

    public CountryAdapter(List<Country> countries) {
        mCountries = countries;
    }
    OnItemClickListener mItemClickListener;
    public void addCountry(Country country) {
        mCountries.add(country);
        notifyDataSetChanged();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new Holder(
                MaterialRippleLayout.on(inflater.inflate(R.layout.item_row,parent,false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(0xFF585858)
                .rippleHover(true)
                .create()
        );
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        Country currentcountry = mCountries.get(position);
        holder.mName.setText(currentcountry.mName);
        holder.mCapital.setText(currentcountry.mCapital);
        String code = currentcountry.mCode;
        String firbaseurl =  "https://countryurllist.firebaseio.com/worldcountries/" + code;
        Firebase myFirebaseRef = new Firebase(firbaseurl);
        myFirebaseRef.child("flag").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final Object flagobject = snapshot.getValue();
                if(flagobject == null){
                    Picasso.with(holder.itemView.getContext()).load("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/No_flag.svg/225px-No_flag.svg.png").into(holder.mImage);
                }
                else {
                    final String flagurl = flagobject.toString();
                    Picasso.with(holder.itemView.getContext()).load(flagurl).into(holder.mImage);
                }

            }
            @Override public void onCancelled(FirebaseError error) { }
        });
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, ImageView mImage);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView mName, mCapital;
        public ImageView mImage;

        public Holder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.countryname);
            mCapital = (TextView) itemView.findViewById(R.id.capital);
            mImage = (ImageView) itemView.findViewById(R.id.countryimage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImage.setTransitionName("transitionimage");
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition(),mImage);
                    }
                }
            });
        }
    }
}