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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aishwarya.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class FlagQuizQuestion3Fragment extends Fragment {

    View rootView = null;
    ImageView question1;
    Button submitflag;
    TextView correctincorrect;
    public static FlagQuizQuestion3Fragment newInstance() {
        FlagQuizQuestion3Fragment fragment = new FlagQuizQuestion3Fragment();
        return fragment;
    }

    public FlagQuizQuestion3Fragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_questionsofflags,container,false);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.fancy_animation);
        set.setTarget(rootView);
        set.start();
        final OnFlagQuizQuestion3ButtonClicked mListener;
        try{
            mListener = (OnFlagQuizQuestion3ButtonClicked)getContext();
        } catch(ClassCastException e){
            throw new ClassCastException("The hosting activity of the Fragment forgot to implement InFragmentInteractionListener");
        }
        question1 = (ImageView)rootView.findViewById(R.id.flagquestion);
        Picasso.with(getContext()).load("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Portugal.svg/200px-Flag_of_Portugal.svg.png").into(question1);
        RadioGroup rbtnGrp = (RadioGroup)rootView.findViewById(R.id.q1radiobox);
        String options = "Portugal,Singapore,Zimbabwe,Romania";
        String[] strArrtext = options.split(",");
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(strArrtext[i]);
        }
        correctincorrect = (TextView)rootView.findViewById(R.id.correctincorrect);
        submitflag = (Button)rootView.findViewById(R.id.submitquiz);
        submitflag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RadioButton rb = (RadioButton)rootView.findViewById(R.id.q1a1);

                if (rb.isChecked()) {
                    correctincorrect.setText("CORRECT");
                    correctincorrect.setTextColor(Color.parseColor("#4CAF50"));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onFlagQuizQuestion3ButtonClicked(true);
                        }
                    }, 1000);
                }
                else {
                    correctincorrect.setText("INCORRECT");
                    correctincorrect.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onFlagQuizQuestion3ButtonClicked(false);
                        }
                    }, 1000);
                }
            }
        });


        return rootView;
    }

    public interface OnFlagQuizQuestion3ButtonClicked{
        public void onFlagQuizQuestion3ButtonClicked(boolean value);
    }



}