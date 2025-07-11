package com.example.quizz.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityMainBinding;
import com.example.quizz.databinding.ActivityQuizBinding;
import com.example.quizz.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private SharedPreferences prefs;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);

        String prenom = getIntent().getStringExtra("prenom");

        questions = new ArrayList<>();
        questions.add(new Question(
                "quelle est le prenom de l'empereur français Napoleon ?",
                Arrays.asList("Bonaparte", "Phillipe", "François", "autre"),
                3
        ));
        questions.add(new Question(
                "quelle est la couleur du cheval blanc d'henri IV ?",
                Arrays.asList("Noir", "Beige", "Blanc", "Mauve"),
                2
        ));
        questions.add(new Question(
                "Lors d'une course de vélo, un cycliste double le deuxième ? Il devient...",
                Arrays.asList("Premier", "Deuxieme", "Il tombe donc dernier", "Cinquiemes"),
                1
        ));
        questions.add(new Question(
                "Si un avion s’écrase à la frontière entre la France et l’Espagne, où enterre-t-on les survivants ?",
                Arrays.asList("Espagne", "France", "Comme on veux", "Nul part"),
                3
        ));
        questions.add(new Question(
                "Si tu as une seule allumette et que tu entres dans une pièce contenant une bougie, une lampe à pétrole et un feu de bois, que dois-tu allumer en premier ?",
                Arrays.asList("La bougie", "La lampe a petrole", "l'allumette", "le feux de bois"),
                2
        ));
        questions.add(new Question(
                "Combien y a-t-il de jours dans une année non bissextile ?",
                Arrays.asList("364", "365", "366", "360"),
                1
        ));
        questions.add(new Question(
                "Lequel de ces personnages historiques est mort avant la naissance des autres ?",
                Arrays.asList("Cléopâtre VII", "Confucius", "Alexandre le Grand", "Jules César"),
                1
        ));



        afficherQuestion();

        binding.suivantButton.setOnClickListener(v -> {
            int checkedId = binding.radioGroup.getCheckedRadioButtonId();
            if (checkedId != -1) {
                int selectedIndex = binding.radioGroup.indexOfChild(findViewById(checkedId));
                if (questions.get(currentQuestionIndex).getIndexCorrect() == selectedIndex) {
                    score++;
                    binding.resultText.setText("Bonne réponse !");
                } else {
                    binding.resultText.setText("Mauvaise réponse !");
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    afficherQuestion();
                } else {
                    prefs.edit().putInt("score", score).apply();
                    binding.resultText.setText("Quiz terminé ! Score de" + prenom + " : " + score);
                    binding.suivantButton.setEnabled(false);
                }
            }

        });
        binding.retourMenuButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void afficherQuestion() {
        Question q = questions.get(currentQuestionIndex);
        binding.questionText.setText(q.getTexte());
        binding.radioGroup.removeAllViews();
        for (String reponse : q.getReponses()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(reponse);
            binding.radioGroup.addView(rb);
        }
        binding.resultText.setText("");


        if (countDownTimer != null) countDownTimer.cancel();


        countDownTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timerText.setText("Temps restant : " + millisUntilFinished / 1000 + "s");
            }
            public void onFinish() {
                binding.timerText.setText("Temps écoulé !");
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    afficherQuestion();
                } else {
                    prefs.edit().putInt("score", score).apply();
                    binding.resultText.setText("Quiz terminé ! Score : " + score);
                    binding.suivantButton.setEnabled(false);
                }
            }
        }.start();}}



