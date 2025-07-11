package com.example.quizz.model;

import java.util.List;
public class Question {
    private final String texte;
    private final List<String> reponses;
    private final int index;

    public Question(String texte, List<String> reponses, int index) {
        this.texte = texte;
        this.reponses = reponses;
        this.index = index;
    }

    public String getTexte() { return texte; }
    public List<String> getReponses() { return reponses; }
    public int getIndex() { return index; }
}
