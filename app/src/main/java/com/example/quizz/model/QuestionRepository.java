package com.example.quizz.model;

import android.util.Log;
import com.example.quizz.R;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionRepository {
    private static final String TAG = "QuestionRepository";
    private static List<Question> questions;

    public static void initializeQuestions(Context context) {
        questions = new ArrayList<>(Arrays.asList(
                new Question(context.getString(R.string.question_1), Arrays.asList(context.getResources().getStringArray(R.array.question_1_answers)), 3),
                new Question(context.getString(R.string.question_2), Arrays.asList(context.getResources().getStringArray(R.array.question_2_answers)), 2),
                new Question(context.getString(R.string.question_3), Arrays.asList(context.getResources().getStringArray(R.array.question_3_answers)), 1),
                new Question(context.getString(R.string.question_4), Arrays.asList(context.getResources().getStringArray(R.array.question_4_answers)), 3),
                new Question(context.getString(R.string.question_5), Arrays.asList(context.getResources().getStringArray(R.array.question_5_answers)), 2),
                new Question(context.getString(R.string.question_6), Arrays.asList(context.getResources().getStringArray(R.array.question_6_answers)), 1),
                new Question(context.getString(R.string.question_7), Arrays.asList(context.getResources().getStringArray(R.array.question_7_answers)), 1)
        ));
        Log.d(TAG, "Questions initialisées : " + questions.size() + " questions chargées.");
    }

    public static List<Question> getQuestions() {
        Log.d(TAG, "Récupération de la liste des questions.");
        return questions;
    }

    public static int getQuestionsCount() {
        int count = questions != null ? questions.size() : 0;
        Log.d(TAG, "Nombre de questions : " + count);
        return count;
    }
}