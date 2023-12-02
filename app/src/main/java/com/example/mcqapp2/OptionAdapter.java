package com.example.mcqapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {


    Context context;
    Questions questions;
    List<String> options;
    public OnTextViewClickListener onTextViewClickListener;
    interface OnTextViewClickListener {
        void onTextViewClick(int position);
    }


    public OptionAdapter(Context context,Questions questions, OnTextViewClickListener onTextViewClickListener) {
        this.onTextViewClickListener = onTextViewClickListener;
        this.context = context;
        this.questions = questions;
        this.options = Arrays.asList(questions.getOption1(),questions.getOption2(),questions.getOption3(),questions.getOption4());
    }


    //List<String> option = Arrays.asList(questions.option1,questions.option2,questions.option3,questions.option4);

    @NonNull
    @Override
    public OptionAdapter.OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.option_item,parent,false);
        OptionViewHolder viewHolder = new OptionViewHolder(v,onTextViewClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionAdapter.OptionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.quizOption.setText(options.get(position));


        holder.itemView.setOnClickListener(view -> {
//            if(onItemClickListener!=null){
//                onItemClickListener.onClick(position);
//            }
//                Toast.makeText(context, options.get(position), Toast.LENGTH_SHORT).show();
            Log.d("POSITION", String.valueOf(holder.getAdapterPosition()));
            onTextViewClickListener.onTextViewClick(holder.getAdapterPosition());
            questions.setUserAnswer(options.get(position));
            notifyDataSetChanged();
        });
        if(questions.userAnswer == options.get(position)){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {
        OnTextViewClickListener onTextViewClickListener;
        TextView quizOption;
        public OptionViewHolder(@NonNull View itemView,OnTextViewClickListener onTextViewClickListener) {
            super(itemView);
            this.onTextViewClickListener = onTextViewClickListener;
            quizOption = itemView.findViewById(R.id.quiz_option);
        }
    }
}
