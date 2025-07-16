# Quizz ESGI

Application Android de quiz réalisée dans le cadre du cours de développement android à l’ESGI.

---

## Présentation

Cette application permet à l’utilisateur de répondre à un QCM, d’évaluer ses connaissances et d’obtenir un score.

---

## Fonctionnalités principales

- **Écran d’accueil** : Saisie du prénom de l’utilisateur.
- **Écran de quiz** : 7 questions à choix multiples (4 réponses possibles, une seule correcte).
- **Écran de résultat** :
    - Affichage du score
    - Message personnalisé selon la performance
    - Récapitulatif des réponses
    - Bouton pour rejouer ou revenir à l’accueil
- **Calcul automatique du score** (1 point par bonne réponse)
- **Persistance** : Prénom et scores mémorisés via `SharedPreferences`
- **Interface** : Conçue en XML, avec ViewBinding activé
- **Accessibilité** : Chaînes de caractères et valeurs externalisées dans les fichiers de ressources (`strings.xml`, `colors.xml`, etc.)
- **Architecture** : Séparation claire des responsabilités (MVC)
- **Multilingue** : français et anglais
- Mise en évidence des bonnes/mauvaises réponses à la fin

---

## Structure du projet
Accueil : MainActivity.java

Quiz : QuizActivity.java

Résultat : ResultActivity.java

Modèle : Question.java

Ressources : res/values/strings.xml, colors.xml, etc.

Persistance : Utilisation de SharedPreferences pour stocker le prénom et les scores.

---

## Installation et lancement

1. **Cloner le projet** :
   ```bash
    git clone <url-du-repo> sur un émulateur ou un appareil Android.
   ```
2. **Ouvrir dans Android Studio ou IntelliJ IDEA**
    
3. **Lancer l’application sur un émulateur ou un appareil Android**

---