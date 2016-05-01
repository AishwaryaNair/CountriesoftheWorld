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
public class FlagquizFragment extends Fragment{
    View rootView = null;
    Button startquiz;
    public static FlagquizFragment newInstance() {
        FlagquizFragment fragment = new FlagquizFragment();
        return fragment;
    }

    public FlagquizFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_flagquiz,container,false);
        final OnFlagButtonClicked mListener;
        try{
            mListener = (OnFlagButtonClicked)getContext();
        } catch(ClassCastException e){
            throw new ClassCastException("The hosting activity of the Fragment forgot to implement InFragmentInteractionListener");
        }

        startquiz = (Button)rootView.findViewById(R.id.startflagquiz);
        startquiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.onFlagButtonClicked();
            }
        });
        return rootView;
    }

    public interface OnFlagButtonClicked{
        public void onFlagButtonClicked();
    }

}