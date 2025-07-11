package com.example.quizz.model;

import java.util.List;

public class Question {
    private final String question;
    private final List<String> answer;
    private final int correctIndex;

    public Question(String question, List<String> answer, int correctIndex) {
        this.question = question;
        this.answer = answer;
        this.correctIndex = correctIndex;
    }
    public int getCorrectAnswerIndex() {
        return correctIndex;
    }
    public String getQuestion() {
        return this.question;
    }
    public List<String> getAnswer() {
        return this.answer;
    }
}
