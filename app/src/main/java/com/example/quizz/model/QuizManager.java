package com.example.quizz.model;

import java.util.List;

public class QuizManager {
    private final List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public QuizManager(List<Question> questions) {
        this.questions = questions;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size() - 1;
    }

    public void nextQuestion() {
        if (hasNextQuestion()) currentQuestionIndex++;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}