package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DecisionTreeActivity extends AppCompatActivity {

    DecisionTreeCanva decisionTreeCanva ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_tree);

        decisionTreeCanva = new DecisionTreeCanva(this);
        setContentView(decisionTreeCanva);
    }
}