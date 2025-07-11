package com.example.quizz.model;

import java.util.List;
public class Question {
    private String texte;
    private List<String> reponses;
    private int indexCorrect;

    public Question(String texte, List<String> reponses, int indexCorrect) {
        this.texte = texte;
        this.reponses = reponses;
        this.indexCorrect = indexCorrect;
    }

    public String getTexte() { return texte; }
    public List<String> getReponses() { return reponses; }
    public int getIndexCorrect() { return indexCorrect; }
}
