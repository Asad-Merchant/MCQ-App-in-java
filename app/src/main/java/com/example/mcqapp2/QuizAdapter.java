package com.example.mcqapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {


    Context context;
    List<Quiz> quiz;

    public QuizAdapter(Context context, List<Quiz> quiz){
        this.context = context;
        this.quiz = quiz;
    }

//    public void updateData(ArrayList<Quiz> newData){
//        this.quiz = newData;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.quiz_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.img.setImageResource(IconPicker.getCurrentIcon());
        holder.cardView.setCardBackgroundColor(Color.parseColor(ColorPicker.getCurrentColor()));
        holder.text.setText(quiz.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,QuestionActivity.class);
                intent.putExtra("DATE",quiz.get(position).title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quiz.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView text;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardContainer);
        }
    }

}
