package com.example.quizz.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE);

        binding.btnStart.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            } else {
                prefs.edit().putString("user_name", name).apply();
                Intent intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}