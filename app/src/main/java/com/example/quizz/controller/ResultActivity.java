package com.example.quizz.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityResultBinding;
import com.example.quizz.model.Question;
import com.example.quizz.model.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    private ActivityResultBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupération du score
        int score = 0;
        int total = QuestionRepository.getQuestionsCount();
        Log.d(TAG, "Nombre total de questions : " + total);

        // Récupération des réponses de l'utilisateur
        ArrayList<Integer> userAnswers = getIntent().getIntegerArrayListExtra("userAnswers");
        Log.d(TAG, "Réponses utilisateur récupérées : " + (userAnswers != null ? userAnswers.size() : 0));
        List<Question> questions = QuestionRepository.getQuestions();

        // Efface les vues précédentes
        binding.resultList.removeAllViews();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            assert userAnswers != null;
            int userAnswer = userAnswers.get(i);
            boolean isCorrect = userAnswer == q.getCorrectAnswerIndex();

            if (isCorrect) {
                score++;
            }
            Log.d(TAG, "Question " + (i + 1) + ": réponse utilisateur = " + userAnswer + ", correcte = " + q.getCorrectAnswerIndex() + ", isCorrect = " + isCorrect);

            // Question
            TextView questionView = new TextView(this);
            questionView.setText(getString(R.string.question_format, i + 1, q.getQuestion()));
            binding.resultList.addView(questionView);

            // Réponse utilisateur
            TextView answerView = new TextView(this);
            if (userAnswer >= 0 && userAnswer < q.getAnswer().size()) {
                answerView.setText(getString(R.string.user_answer_format, q.getAnswer().get(userAnswer)));
            } else {
                answerView.setText(getString(R.string.user_answer_format, getString(R.string.no_answer)));
            }
            answerView.setTextColor(isCorrect
                    ? getResources().getColor(android.R.color.holo_green_dark, getTheme())
                    : getResources().getColor(android.R.color.holo_red_dark, getTheme()));
            binding.resultList.addView(answerView);

            // Correction si réponse fausse
            if (!isCorrect) {
                TextView correctionView = new TextView(this);
                correctionView.setText(getString(R.string.correct_answer_format, q.getAnswer().get(q.getCorrectAnswerIndex())));
                correctionView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark, getTheme()));
                binding.resultList.addView(correctionView);
            }
        }

        // Affichage du score
        binding.resultScore.setText(getString(R.string.score_format, score, total));

        // Récupération du prénom
        prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE);
        String name = prefs.getString("user_name", "");

        // Message personnalisé selon le score
        String message;
        if (score >= 5) {
            message = getString(R.string.congrats, name);
        } else {
            message = getString(R.string.encouragement, name);
        }
        binding.resultMessage.setText(message);

        // Bouton Rejouer
        binding.btnReplay.setOnClickListener(v -> {
            Log.i(TAG, "Bouton Rejouer cliqué");
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        // Bouton Accueil
        binding.btnHome.setOnClickListener(v -> {
            Log.i(TAG, "Bouton Accueil cliqué");
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}