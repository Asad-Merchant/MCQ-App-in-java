package com.example.mcqapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class AnswerActivity extends AppCompatActivity {
    double per;
    TextView forScore,percent;
    Button next;
    int index = 1;
    int score = 0;
    Map<String,Object> questions;
    Map<String,Object> question;
    ArrayList<String> answer = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        forScore = findViewById(R.id.score);
        next = findViewById(R.id.btn_continue);
        percent = findViewById(R.id.percentText);
        answer.add(0,null);
        Intent intent = getIntent();
        question = (Map<String, Object>) intent.getSerializableExtra("question");
        answer = (ArrayList<String>) intent.getSerializableExtra("answer");
        Log.d("NUMBER", String.valueOf(question.size()));

        for(index=1;index<=question.size();index++){
            questions = (Map<String, Object>) question.get("question"+index);
            String correctAnswer = questions.get("answer").toString();
            if(answer.get(index).equals(correctAnswer)){
                score++;
            }
        }
        per = ((double) score/question.size())*100;
        forScore.setText("Your Score is "+score+" out of "+question.size());
        String formattedValue = String.format("%.2f",per);
        percent.setText("Percentage: "+formattedValue+"%");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnswerActivity.this,MainActivity.class));
                finish();
            }
        });
//        Log.d("Question", question.toString());
//        Log.d("ANSWER1",answer.get(1));
//        Log.d("ANSWER1",answer.get(2));
//        Log.d("ANSWER1",answer.get(3));

    }
}