package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class ChatBotController {
    @FXML
    private TextArea textAreaChat;

    @FXML
    private TextField textFieldChat;
    private Map<String, String> questionsReponses = new HashMap<>();

    // Méthode d'initialisation du contrôleur
    @FXML
    public void initialize() {
        // Initialisez les questions et les réponses
        questionsReponses.put("Bonjour", "Bonjour ! Comment puis-je vous aider ?");
        questionsReponses.put("Cava?", "Je suis juste un bot, donc je n'ai pas d'émotions, mais je suis prêt à vous aider !");
        questionsReponses.put(" Activités ?", "Concert, expo d'art, foire alimentaire.");
        questionsReponses.put("Quels sont les horaires des transports en commun ?", " Les horaires sont disponibles sur notre application.");
    }
    @FXML
    void envoyerChat(ActionEvent event) {
        // Récupérer la question saisie dans le TextField
        String question = textFieldChat.getText();

        // Rechercher la réponse correspondante dans la Map
        String reponse = questionsReponses.getOrDefault(question, "Désolé, je ne comprends pas cette question.");

        // Afficher la question et la réponse dans le TextArea du chat
        textAreaChat.appendText("Vous : " + question + "\n");
        textAreaChat.appendText("Bot : " + reponse + "\n");

        // Effacer le contenu du TextField
        textFieldChat.clear();
    }
}
