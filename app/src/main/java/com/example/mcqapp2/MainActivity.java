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
        collectionReference = firestore.collection("quiz");
        recyclerView.setAdapter(adapter);
        query = firestore.collection("quiz");
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

        //quizList.add(new Quiz("1","22-1-2010"));
        //quizList.add(new Quiz("1","22-1-2010"));
        //quizList.add(new Quiz("1","22-1-2010"));
//        quizList.add(new Quiz("1","22-1-2010"));
//        quizList.add(new Quiz("1","22-1-2010"));
//        quizList.add(new Quiz("1","22-1-2010"));
//        quizList.add(new Quiz("1","22-1-2010"));
//        quizList.add(new Quiz("1","22-1-2010"));


//        firestore.collection("quiz").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            QuerySnapshot querySnapshot = task.getResult();
//                            if(querySnapshot != null){
//                                for(QueryDocumentSnapshot document : querySnapshot){
//                                    Quiz quiz = document.toObject(Quiz.class);
//                                    quizList.add(quiz);
//                               }
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//
//                    }
//                });



        //quizList.clear();
//        collectionReference.get().addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                QuerySnapshot querySnapshot = task.getResult();
//                if(querySnapshot != null){
//                    for(QueryDocumentSnapshot document : querySnapshot){
//                        Quiz quiz = document.toObject(Quiz.class);
//                        quizList.add(quiz);
//                    }
//                }
//            } else {
//                Exception e = task.getException();
//                if(e!=null){
//                    e.printStackTrace();
//                }
//            }
//      });

//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Iterate through the query results
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        // Convert Firestore document to a Quiz object
//                        Quiz quiz = document.toObject(Quiz.class);
//                        quizList.add(quiz);
//                    }
//
//                    // Now, quizList contains your Quiz objects fetched from Firestore
//                    // You can use this list as needed in your app
//                } else {
//                    // Handle the error
//                    Exception e = task.getException();
//                    if (e != null) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        adapter.notifyDataSetChanged();
//        collectionReference.addSnapshotListener((value, error) -> {
//            if(value==null || error!=null){
//                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            quizList.addAll(value.toObjects(Quiz.class));
//            adapter.notifyDataSetChanged();
//        });
//
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Iterate through the query results
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        // Convert Firestore document to a Quiz object
//                        Quiz quiz = document.toObject(Quiz.class);
//                        quizList.add(quiz);
//                    }
//
//                    // Now, quizList contains your Quiz objects fetched from Firestore
//                    // You can use this list as needed in your app
//                } else {
//                    // Handle the error
//                    Exception e = task.getException();
//                    if (e != null) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        quizList.clear();
//        FirebaseFirestore.getInstance()
//                .collection("quiz")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot snapshot : snapshotList){
//                            Map<String, Object> datavalue = snapshot.getData();
//                            Quiz q = new Gson().fromJson(datavalue.values().toString(), Quiz.class);
//                            //Object[] values = datavalue.values().toArray();
//                            //for (int i = 0; i < values.length; i++) {
//                                //Log.d("Document",values[i].toString());
//
//                            //}
//                            //String json = new Gson().toJson(object);
//                            //HashMap<String, Object> valueMap = snapshot.getData().values().toArray()[0];
//                            //String date = snapshot.getString("title");
//                            //Quiz quiz = snapshot.toObject(Quiz.class);
//                            //quizList.put(date,quiz);
//                           Log.d("Document",snapshot.getData().toString());
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });

//        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if(!queryDocumentSnapshots.isEmpty()){
//                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                        Log.d("Document",documentSnapshot.toObject(Quiz.class).toString());
//                    }
//                }
//            }
//        });







//        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
//
//
//              quizList.addAll(queryDocumentSnapshots.toObjects(Quiz.class));
//              adapter.notifyDataSetChanged();
//            List<Quiz> quizzes = queryDocumentSnapshots.toObjects(Quiz.class);
//            Log.d("Document",queryDocumentSnapshots.toObjects(Quiz.class).toString());
//            System.out.println(quizzes.toString());
//        });


//        String answer = (String) questions.get("answer");
//        String des = (String) questions.get("description");
//        String option1 = (String) questions.get("option1");
//        String option2 = (String) questions.get("option2");
//        String option3 = (String) questions.get("option3");
//        String option4 = (String) questions.get("option4");

//        Log.d("Question","answer"+answer);
//        Log.d("Question","description"+des);
//        Log.d("Question","option1"+option1);
//        Log.d("Question","option2"+option2);
//        Log.d("Question","option3"+option3);
//        Log.d("Question","option4"+option4);













    }
}