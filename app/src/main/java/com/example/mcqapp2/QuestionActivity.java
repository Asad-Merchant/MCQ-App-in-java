package com.example.mcqapp2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity implements OptionAdapter.OnTextViewClickListener{
    OptionAdapter optionAdapter;
    String[] questionOption = new String[4];
    ArrayList<String> answer2 = new ArrayList<>();
    Button sub,next,prev;
    Map<String,Object> quizzes;
    Map<String,Object> questions;
    Map<String,Object> options;
    int index = 1;
    RecyclerView option;
    TextView des;
    int total = 26;

    FirebaseFirestore firestore;
    CollectionReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        firestore = FirebaseFirestore.getInstance();
        option = findViewById(R.id.optionList);
        des = findViewById(R.id.description);
        sub = findViewById(R.id.btnSub);
        next = findViewById(R.id.btnNext);
        prev = findViewById(R.id.btnPrev);
        reference = firestore.collection("quiz");
        for(int i=0;i<total;i++){
            answer2.add(i,"IIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
//                Toast.makeText(QuestionActivity.this, answer2.get(index), Toast.LENGTH_SHORT).show();
                myData();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                myData();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ANSWER",answer2.get(1));
                Log.d("ANSWER",answer2.get(2));
                Log.d("ANSWER",answer2.get(3));
                Intent intent = new Intent(QuestionActivity.this,AnswerActivity.class);
                intent.putExtra("answer",answer2);
                intent.putExtra("question",(Serializable) questions);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");
        if(date != null){
            reference.whereEqualTo("title",date)
                    .get()
                    .addOnSuccessListener((documentSnapshots) -> {
                        if(documentSnapshots != null && !documentSnapshots.isEmpty()){
                            List<DocumentSnapshot> documents = documentSnapshots.getDocuments();
//                            Log.d("DATA",documentSnapshots.getDocuments().toString());
                            quizzes = documents.get(0).getData();
                            questions = (Map<String, Object>) quizzes.get("Question");
//                            Log.d("DATA",quizzes.get("Question").toString());
                            myData();

//                            Log.d("DATA",quizzes.toString());
//                            Questions questions1 = questions.get("question"+index);
//                            //                                  Log.d("DATA", String.valueOf(questions1));
//                            if(questions1!=null){
//                                des.setText(questions1.getDescription());
//                                OptionAdapter optionAdapter = new OptionAdapter(this,questions1);
//                                option.setLayoutManager(new LinearLayoutManager(this));
//                                option.setAdapter(optionAdapter);
//                                option.setHasFixedSize(true);
//                            }
                        }
                    });
        }else {
            Toast.makeText(this, "Date not found", Toast.LENGTH_SHORT).show();
        }



    }

    private void storeAnswer(int position) {
        String ans = questionOption[position];
        answer2.add(index,ans);
        Log.d("POSITION",answer2.get(index));
    }

    public void myData(){
        options = (Map<String,Object>) questions.get("question"+index);
//        Log.d("DATA",questions.get("question"+index).toString());
        String description = options.get("description").toString();
        String option1 = options.get("option1").toString();
        String option2 = options.get("option2").toString();
        String option3 = options.get("option3").toString();
        String option4 = options.get("option4").toString();
        String answer = options.get("answer").toString();
        questionOption[0] = option1;
        questionOption[1] = option2;
        questionOption[2] = option3;
        questionOption[3] = option4;

        sub.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        prev.setVisibility(View.GONE);
        if(index==1){
            next.setVisibility(View.VISIBLE);
        } else if (index == questions.size()) {
            sub.setVisibility(View.VISIBLE);
            prev.setVisibility(View.VISIBLE);
        } else {
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }

        Questions questions1 = new Questions(description,option1,option2,option3,option4,answer);
        des.setText(questions1.getDescription());
        optionAdapter = new OptionAdapter(this,questions1, this);

        option.setLayoutManager(new LinearLayoutManager(this));
        option.setAdapter(optionAdapter);
        option.setHasFixedSize(true);

    }


    @Override
    public void onTextViewClick(int position) {
//        Toast.makeText(this, "Clicked position is "+position, Toast.LENGTH_SHORT).show();
        storeAnswer(position);
    }
}