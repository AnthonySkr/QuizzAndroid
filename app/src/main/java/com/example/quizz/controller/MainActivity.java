package com.example.quizz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityMainBinding;
import com.example.quizz.model.QuestionRepository;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialisation des questions
        QuestionRepository.initializeQuestions(this);
        Log.d(TAG, "Questions initialisées.");

        prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);

        binding.btnStart.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            if (name.isEmpty()) {
                Snackbar.make(binding.getRoot(), getString(R.string.enter_name), Snackbar.LENGTH_SHORT).show();
                Log.d(TAG, "Tentative de démarrage sans nom.");
            } else {
                prefs.edit().putString("user_name", name).apply();
                Log.d(TAG, "Nom enregistré : " + name + ". Lancement du quiz.");
                Intent intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}