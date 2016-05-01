package com.example.aishwarya.myapplication.quizzes;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.example.aishwarya.myapplication.R;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class CapitalQuizQuestion3Fragment extends Fragment {

    View rootView = null;
    TextView question1;
    Button submitcapital;
    TextView correctincorrect;
    public static CapitalQuizQuestion3Fragment newInstance() {
        CapitalQuizQuestion3Fragment fragment = new CapitalQuizQuestion3Fragment();
        return fragment;
    }

    public CapitalQuizQuestion3Fragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_questionsofcapital,container,false);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fancy_animation);
        set.setTarget(rootView);
        set.start();
        final OnCapitalQuizQuestion3ButtonClicked mListener;
        try{
            mListener = (OnCapitalQuizQuestion3ButtonClicked)getContext();
        } catch(ClassCastException e){
            throw new ClassCastException("The hosting activity of the Fragment forgot to implement InFragmentInteractionListener");
        }
        question1 = (TextView)rootView.findViewById(R.id.capitalquestion);
        question1.setText("Greece");
        RadioGroup rbtnGrp = (RadioGroup)rootView.findViewById(R.id.q1radiobox);
        String options = "Athens,Budapest,Nairobi,Beirut";
        String[] strArrtext = options.split(",");
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(strArrtext[i]);
        }
        correctincorrect = (TextView)rootView.findViewById(R.id.correctincorrect);
        submitcapital = (Button)rootView.findViewById(R.id.submitcapital);
        submitcapital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RadioButton rb = (RadioButton)rootView.findViewById(R.id.q1a1);

                if (rb.isChecked()) {
                    correctincorrect.setText("CORRECT");
                    correctincorrect.setTextColor(Color.parseColor("#4CAF50"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onCapitalQuizQuestion3ButtonClicked(true);
                        }
                    }, 1000);

                } else {
                    correctincorrect.setText("INCORRECT");
                    correctincorrect.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onCapitalQuizQuestion3ButtonClicked(false);
                        }
                    }, 1000);
                }
            }
        });


        return rootView;
    }

    public interface OnCapitalQuizQuestion3ButtonClicked{
        public void onCapitalQuizQuestion3ButtonClicked(boolean value);
    }

}
