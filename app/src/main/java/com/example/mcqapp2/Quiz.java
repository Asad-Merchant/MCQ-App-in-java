package com.example.mcqapp2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class Quiz {
    public String id;
    public String title;
    // List<Questions> questions = new ArrayList<>();
    public Map<String, Questions> questions = new HashMap<>();

    public Quiz(String id, String title, Map<String, Questions> questions) {
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, Questions> questions) {
        this.questions = questions;
    }

    public Quiz() {
    }
}


