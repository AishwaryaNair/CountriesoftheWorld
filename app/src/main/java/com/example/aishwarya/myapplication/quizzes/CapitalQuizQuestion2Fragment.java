package com.example.aishwarya.myapplication.quizzes;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aishwarya.myapplication.R;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class CapitalQuizQuestion2Fragment extends Fragment {

    View rootView = null;
    TextView question1;
    Button submitcapital;
    TextView correctincorrect;
    public static CapitalQuizQuestion2Fragment newInstance() {
        CapitalQuizQuestion2Fragment fragment = new CapitalQuizQuestion2Fragment();
        return fragment;
    }

    public CapitalQuizQuestion2Fragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_questionsofcapital,container,false);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fancy_animation);
        set.setTarget(rootView);
        set.start();
        final OnCapitalQuizQuestion2ButtonClicked mListener;
        try{
            mListener = (OnCapitalQuizQuestion2ButtonClicked)getContext();
        } catch(ClassCastException e){
            throw new ClassCastException("The hosting activity of the Fragment forgot to implement InFragmentInteractionListener");
        }
        question1 = (TextView)rootView.findViewById(R.id.capitalquestion);
        question1.setText("Turkey");
        RadioGroup rbtnGrp = (RadioGroup)rootView.findViewById(R.id.q1radiobox);
        String options = "Riyadh,Abu Dhabi,Ankara,Vienna";
        String[] strArrtext = options.split(",");
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(strArrtext[i]);
        }
        correctincorrect = (TextView)rootView.findViewById(R.id.correctincorrect);
        submitcapital = (Button)rootView.findViewById(R.id.submitcapital);
        submitcapital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RadioButton rb = (RadioButton)rootView.findViewById(R.id.q1a3);

                if (rb.isChecked()) {
                    correctincorrect.setText("CORRECT");
                    correctincorrect.setTextColor(Color.parseColor("#4CAF50"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onCapitalQuizQuestion2ButtonClicked(true);
                        }
                    }, 1000);
                }
                else {
                    correctincorrect.setText("INCORRECT");
                    correctincorrect.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onCapitalQuizQuestion2ButtonClicked(false);
                        }
                    }, 1000);
                }
            }
        });


        return rootView;
    }

    public interface OnCapitalQuizQuestion2ButtonClicked{
        public void onCapitalQuizQuestion2ButtonClicked(boolean value);
    }



}
