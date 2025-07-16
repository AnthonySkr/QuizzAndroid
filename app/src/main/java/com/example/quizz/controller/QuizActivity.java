package com.example.quizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizz.R;
import com.example.quizz.databinding.ActivityQuizBinding;
import com.example.quizz.model.Question;
import com.example.quizz.model.QuestionRepository;
import com.example.quizz.model.QuizManager;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private ActivityQuizBinding binding;
    private QuizManager quizManager;
    private CountDownTimer countDownTimer;
    private final List<Integer> userAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quizManager = new QuizManager(QuestionRepository.getQuestions());
        Log.d(TAG, "QuizActivity créée, QuizManager initialisé.");

        afficherQuestion();

        binding.btnNext.setOnClickListener(v -> {
            int checkedId = binding.radioGroup.getCheckedRadioButtonId();
            if (checkedId != -1) {
                int selectedIndex = binding.radioGroup.indexOfChild(findViewById(checkedId));
                userAnswers.add(selectedIndex); // Enregistre la réponse
                Log.d(TAG, "Réponse enregistrée : index " + selectedIndex);

                if (quizManager.hasNextQuestion()) {
                    quizManager.nextQuestion();
                    afficherQuestion();
                } else {
                    Log.i(TAG, "Dernière question atteinte, affichage des résultats.");
                    showResults();
                }
            }
        });

        binding.btnBackToMenu.setOnClickListener(v -> {
            Log.i(TAG, "Retour au menu demandé.");
            finish();
        });
    }

    private void afficherQuestion() {
        Question q = quizManager.getCurrentQuestion();
        Log.d(TAG, "Affichage de la question : " + q.getQuestion());
        binding.questionText.setText(q.getQuestion());

        binding.radioGroup.removeAllViews();
        for (String response : q.getAnswer()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(response);
            binding.radioGroup.addView(rb);
        }
        binding.radioGroup.clearCheck();
        binding.resultText.setText("");

        binding.btnNext.setEnabled(false); // Désactive le bouton par défaut
        binding.btnNext.setAlpha(0.5f);   // Apparence visuelle désactivée

        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            boolean enabled = checkedId != -1;
            binding.btnNext.setEnabled(enabled);
            binding.btnNext.setAlpha(enabled ? 1.0f : 0.5f); // Apparence activée/désactivée
        });

        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(14000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timerText.setText(getString(R.string.textTimer, millisUntilFinished / 1000));
            }
            public void onFinish() {
                binding.timerText.setText(R.string.endTime);

                // À la fin du timer, enregistre la réponse sélectionnée ou -1 si aucune
                int checkedId = binding.radioGroup.getCheckedRadioButtonId();
                if (checkedId != -1) {
                    int selectedIndex = binding.radioGroup.indexOfChild(findViewById(checkedId));
                    userAnswers.add(selectedIndex);
                    Log.d(TAG, "Timer terminé, réponse enregistrée : index " + selectedIndex);
                } else {
                    Log.d(TAG, "Timer terminé, aucune réponse sélectionnée.");
                    userAnswers.add(-1);
                }

                if (quizManager.hasNextQuestion()) {
                    quizManager.nextQuestion();
                    afficherQuestion();
                } else {
                    Log.d(TAG, "Fin du quiz atteinte via timer, affichage des résultats.");
                    showResults();
                }
            }
        }.start();
        Log.d(TAG, "Timer démarré pour la question.");
    }

    private void showResults() {
        Log.d(TAG, "Navigation vers ResultActivity avec " + userAnswers.size() + " réponses.");
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("userAnswers", new ArrayList<>(userAnswers));

        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
        binding = null;
    }
}