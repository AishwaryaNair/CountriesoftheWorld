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
public class CapitalQuizActivity extends AppCompatActivity implements CapitalQuizQuestionFragment.OnCapitalQuizQuestion1ButtonClicked,CapitalQuizQuestion2Fragment.OnCapitalQuizQuestion2ButtonClicked,CapitalQuizQuestion3Fragment.OnCapitalQuizQuestion3ButtonClicked,FinalScoreFragment.OnFinalButtonClicked {
    Integer finalscore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitalquiz);
        finalscore = 0;

        if (savedInstanceState == null) {
            CapitalQuizQuestionFragment capitalQuizQuestionFragment = new CapitalQuizQuestionFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activitycapitalquizcontainer, capitalQuizQuestionFragment)
                    .commit();
        }
    }

    public void onCapitalQuizQuestion1ButtonClicked(boolean value){
        if(value == true) {
            finalscore = finalscore + 1;
        }
        else {
            finalscore = finalscore + 0;
        }
        CapitalQuizQuestion2Fragment capitalQuizQuestion2Fragment = new CapitalQuizQuestion2Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activitycapitalquizcontainer, capitalQuizQuestion2Fragment)
                .commit();
    }

    public void onCapitalQuizQuestion2ButtonClicked(boolean value){
        if(value == true) {
            finalscore = finalscore + 1;
        }
        else {
            finalscore = finalscore + 0;
        }
        CapitalQuizQuestion3Fragment capitalQuizQuestion3Fragment = new CapitalQuizQuestion3Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activitycapitalquizcontainer, capitalQuizQuestion3Fragment)
                .commit();
    }

    public void onCapitalQuizQuestion3ButtonClicked(boolean value){
        if(value == true) {
            finalscore = finalscore + 1;
        }
        else {
            finalscore = finalscore + 0;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activitycapitalquizcontainer, FinalScoreFragment.newInstance(finalscore))
                .commit();
    }

    public void onFinalButtonClicked(){
        Intent intent = new Intent(this, QuizAcitvity.class);
        startActivity(intent);
    }
}
