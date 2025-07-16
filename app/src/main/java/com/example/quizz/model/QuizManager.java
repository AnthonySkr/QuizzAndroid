package com.example.quizz.model;

import android.util.Log;

import java.util.List;

public class QuizManager {
    private static final String TAG = "QuizManager";
    private final List<Question> questions;
    private int currentQuestionIndex = 0;

    public QuizManager(List<Question> questions) {
        this.questions = questions;
        Log.d(TAG, "QuizManager initialisé avec " + questions.size() + " questions.");
    }

    public Question getCurrentQuestion() {
        Log.d(TAG, "Récupération de la question courante à l'index " + currentQuestionIndex);
        return questions.get(currentQuestionIndex);
    }

    public boolean hasNextQuestion() {
        boolean hasNext = currentQuestionIndex < questions.size() - 1;
        Log.d(TAG, "hasNextQuestion: " + hasNext);
        return hasNext;
    }

    public void nextQuestion() {
        if (hasNextQuestion()) {
            currentQuestionIndex++;
            Log.d(TAG, "Passage à la question suivante : index " + currentQuestionIndex);
        } else {
            Log.d(TAG, "Aucune question suivante disponible.");
        }
    }
}