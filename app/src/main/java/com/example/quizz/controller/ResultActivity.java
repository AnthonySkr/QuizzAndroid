package com.example.quizz.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupération du score (via Intent)
        int score = getIntent().getIntExtra("score", 0);

        // Récupération du prénom (depuis SharedPreferences)
        prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE);
        String name = prefs.getString("user_name", "");

        // Affichage du score
        binding.resultScore.setText(getString(R.string.score_format, score));
        /*
        val score = 4 // score de l'utilisateur
        val totalQuestions = 7 // nombre total de questions
        val tvScore = findViewById<TextView>(R.id.tvScore)
        tvScore.text = "Score : $score/$totalQuestions"
        */

        // Message personnalisé selon le score
        String message;
        if (score >= 4) {
            message = getString(R.string.congrats, name);
        } else {
            message = getString(R.string.encouragement, name);
        }
        binding.resultMessage.setText(message);
        /*
        val score = ... // récupère le score de l'utilisateur
        val message = if (score >= 10) "Bravo !" else "Courage, réessaie !"
        findViewById<TextView>(R.id.tvMessage).text = message
         */

        // Bouton Rejouer
        binding.btnReplay.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        // Bouton Accueil
        binding.btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}