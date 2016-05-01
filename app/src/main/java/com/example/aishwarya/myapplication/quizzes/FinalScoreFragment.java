package com.example.aishwarya.myapplication.quizzes;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aishwarya.myapplication.R;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class FinalScoreFragment extends Fragment {
    View rootView = null;
    TextView displayfinalscore;
    Button submitcapital;
    public static FinalScoreFragment newInstance(int finalscore) {
        FinalScoreFragment fragment = new FinalScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("finalscore", finalscore);
        fragment.setArguments(bundle);
        return fragment;
    }

    public FinalScoreFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_finalscore,container,false);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fancy_animation);
        set.setTarget(rootView);
        set.start();
        final OnFinalButtonClicked mListener;
        try{
            mListener = (OnFinalButtonClicked)getContext();
        } catch(ClassCastException e){
            throw new ClassCastException("The hosting activity of the Fragment forgot to implement InFragmentInteractionListener");
        }
        displayfinalscore = (TextView)rootView.findViewById(R.id.displayfinalscore);
        if(getArguments() != null){
            displayfinalscore.setText(String.valueOf(getArguments().getInt("finalscore")));
        }
        submitcapital = (Button)rootView.findViewById(R.id.finalscore);
        submitcapital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                    mListener.onFinalButtonClicked();
            }
        });


        return rootView;
    }

    public interface OnFinalButtonClicked{
        public void onFinalButtonClicked();
    }
}
