package com.example.quizz.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityQuizBinding;
import com.example.quizz.model.Question;
import com.example.quizz.model.QuizManager;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private SharedPreferences prefs;
    private QuizManager quizManager;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE);
        String userName = getIntent().getStringExtra("user_name");

        quizManager = new QuizManager(Arrays.asList(
                new Question("Quel est le prenom de l'empereur français Napoleon ?", Arrays.asList("Bonaparte", "Phillipe", "François", "autre"), 3),
                new Question("Quel est la couleur du cheval blanc d'Henri IV ?", Arrays.asList("Noir", "Beige", "Blanc", "Mauve"), 2),
                new Question("Lors d'une course de vélo, un cycliste double le deuxième ? Il devient...", Arrays.asList("Premier", "Deuxieme", "Il tombe donc dernier", "Cinquiemes"), 1),
                new Question("Si un avion s’écrase à la frontière entre la France et l’Espagne, où enterre-t-on les survivants ?", Arrays.asList("Espagne", "France", "Comme on veux", "Nul part"), 3),
                new Question("Si tu as une seule allumette et que tu entres dans une pièce contenant une bougie, une lampe à pétrole et un feu de bois, que dois-tu allumer en premier ?", Arrays.asList("La bougie", "La lampe a petrole", "l'allumette", "le feux de bois"), 2),
                new Question("Combien y a-t-il de jours dans une année non bissextile ?", Arrays.asList("364", "365", "366", "360"), 1),
                new Question("Lequel de ces personnages historiques est mort avant la naissance des autres ?", Arrays.asList("Cléopâtre VII", "Confucius", "Alexandre le Grand", "Jules César"), 1)
        ));

        afficherQuestion();

        binding.btnNext.setOnClickListener(v -> {
            int checkedId = binding.radioGroup.getCheckedRadioButtonId();
            if (checkedId != -1) {
                int selectedIndex = binding.radioGroup.indexOfChild(findViewById(checkedId));

                if (quizManager.getCurrentQuestion().getIndex() == selectedIndex) {
                    quizManager.incrementScore();
                    binding.resultText.setText("Bonne réponse !");
                } else {
                    binding.resultText.setText("Mauvaise réponse !");
                }

                if (quizManager.hasNextQuestion()) {
                    quizManager.nextQuestion();
                    afficherQuestion();
                } else {
                    prefs.edit().putInt("score", quizManager.getScore()).apply();
                    binding.resultText.setText("Quiz terminé ! Score de " + userName + " : " + quizManager.getScore());
                    binding.btnNext.setEnabled(false);
                }
            }
        });

        binding.retourMenuButton.setOnClickListener(v -> finish());
    }

    private void afficherQuestion() {
        Question q = quizManager.getCurrentQuestion();
        binding.questionText.setText(q.getTexte());

        binding.radioGroup.removeAllViews();
        for (String response : q.getReponses()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(response);
            binding.radioGroup.addView(rb);
        }
        binding.radioGroup.clearCheck();
        binding.resultText.setText("");

        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(7000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timerText.setText(getString(R.string.textTimer, millisUntilFinished / 1000));
            }
            public void onFinish() {
                binding.timerText.setText(R.string.endTime);

                if (quizManager.hasNextQuestion()) {
                    quizManager.nextQuestion();
                    afficherQuestion();
                } else {
                    prefs.edit().putInt("score", quizManager.getScore()).apply();
                    binding.resultText.setText("Quiz terminé ! Score : " + quizManager.getScore());
                    binding.btnNext.setEnabled(false);
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
        binding = null;
    }
}