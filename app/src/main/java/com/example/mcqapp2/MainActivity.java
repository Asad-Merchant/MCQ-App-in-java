package com.example.mcqapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Quiz> quizList = new ArrayList<>();
    RecyclerView recyclerView;
    QuizAdapter adapter;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FloatingActionButton datePicker;
    Query query;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new QuizAdapter((Context) this,quizList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("your_collection_name");
        recyclerView.setAdapter(adapter);
        query = firestore.collection("your_collection_name");
        datePicker = findViewById(R.id.btnDatePicker);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> date = MaterialDatePicker.Builder.datePicker().build();
                date.show(getSupportFragmentManager(),"DatePicker");
                date.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Log.d("DATE",date.getHeaderText());
                        String format = simpleDateFormat.format(new Date(selection));
                        Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
                        intent.putExtra("DATE",format);
                        startActivity(intent);
                    }
                });
                date.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DatePicker",date.getHeaderText());
                    }
                });
                date.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("DatePicker","Date Picker Cancelled");
                    }
                });
            }
        });


        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            //for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
//                Quiz quiz = documentSnapshot.toObject(Quiz.class);
//                Log.d("Doc",quiz.toString());
            quizList.addAll(queryDocumentSnapshots.toObjects(Quiz.class));
            adapter.notifyDataSetChanged();
            //}

        });

    }
}
