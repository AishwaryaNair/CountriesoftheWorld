package com.example.aishwarya.myapplication.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aishwarya.myapplication.R;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class CapitalquizFragment extends Fragment{
    View rootView = null;
    Button startquiz;
    public static CapitalquizFragment newInstance() {
        CapitalquizFragment fragment = new CapitalquizFragment();
        return fragment;
    }

    public CapitalquizFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_capitalquiz,container,false);
        final OnCapitalButtonClicked mListener;
        try{
            mListener = (OnCapitalButtonClicked)getContext();
        } catch(ClassCastException e){
            throw new ClassCastException("The hosting activity of the Fragment forgot to implement InFragmentInteractionListener");
        }

        startquiz = (Button)rootView.findViewById(R.id.startcapitalquiz);
        startquiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.onCapitalButtonClicked();
            }
        });
        return rootView;
    }

    public interface OnCapitalButtonClicked{
        public void onCapitalButtonClicked();
    }

}
