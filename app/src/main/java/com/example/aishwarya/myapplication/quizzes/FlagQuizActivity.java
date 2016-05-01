package com.example.aishwarya.myapplication.quizzes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.aishwarya.myapplication.R;
import com.example.aishwarya.myapplication.navigation.QuizAcitvity;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class FlagQuizActivity extends AppCompatActivity implements FlagQuizQuestionFragment.OnFlagQuizQuestion1ButtonClicked,FlagQuizQuestion2Fragment.OnFlagQuizQuestion2ButtonClicked,FlagQuizQuestion3Fragment.OnFlagQuizQuestion3ButtonClicked,FinalScoreFragment.OnFinalButtonClicked {
    Integer finalscore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagquiz);
        finalscore = 0;

        if (savedInstanceState == null) {
            FlagQuizQuestionFragment flagQuizQuestionFragment = new FlagQuizQuestionFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activityflagquizcontainer, flagQuizQuestionFragment)
                    .commit();
        }
    }

    public void onFlagQuizQuestion1ButtonClicked(boolean value){
        if(value == true) {
            finalscore = finalscore + 1;
        }
        else {
            finalscore = finalscore + 0;
        }
        FlagQuizQuestion2Fragment flagQuizQuestion2Fragment = new FlagQuizQuestion2Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activityflagquizcontainer, flagQuizQuestion2Fragment)
                .commit();
    }

    public void onFlagQuizQuestion2ButtonClicked(boolean value){
        if(value == true) {
            finalscore = finalscore + 1;
        }
        else {
            finalscore = finalscore + 0;
        }
        FlagQuizQuestion3Fragment flagQuizQuestion3Fragment = new FlagQuizQuestion3Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activityflagquizcontainer, flagQuizQuestion3Fragment)
                .commit();
    }

    public void onFlagQuizQuestion3ButtonClicked(boolean value){
        if(value == true) {
            finalscore = finalscore + 1;
        }
        else {
            finalscore = finalscore + 0;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activityflagquizcontainer, FinalScoreFragment.newInstance(finalscore))
                .commit();
    }

    public void onFinalButtonClicked(){
        Intent intent = new Intent(this, QuizAcitvity.class);
        startActivity(intent);
    }
}