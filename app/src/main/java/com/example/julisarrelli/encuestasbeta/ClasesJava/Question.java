package com.example.julisarrelli.encuestasbeta.ClasesJava;

import java.util.ArrayList;

/**
 * Created by julisarrelli on 8/24/17.
 */

public class Question {

    private int idQuestion;
    private String question;
    private String answer;
    private ArrayList<String> options;

    public Question(int idQuestion, String question) {
        this.idQuestion = idQuestion;
        this.question = question;
        options=new ArrayList<String>();

    }


    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void addOption(String option)
    {
        options.add(option);
    }


    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
