/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poep1regandloginapp;

import javax.swing.JOptionPane;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author RC_Student_Lab
 */
public class Message {
     private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessagesSent = 0;
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    // Constructor
    public Message(String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generate random 10-digit ID
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    // Ensure ID is no more than 10 characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Ensure recipient number starts with +27 and has 10 more digits
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+27\\d{9,10}$");
    }

    // Ensure message is ≤ 250 chars
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }

    // Create Message Hash: first 2 of ID : total : first and last words (uppercase)
    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String prefix = messageID.substring(0, 2);
        String hash = prefix + ":" + totalMessagesSent + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Allow user to choose what to do with message
    public String sendMessageOption() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an action for this message:",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0:
                totalMessagesSent++;
                sentMessages.add(this);
                JOptionPane.showMessageDialog(null, "Message successfully sent.");
                return "Message successfully sent.";
            case 1:
                JOptionPane.showMessageDialog(null, "Press 0 to delete message.");
                return "Press 0 to delete message.";
            case 2:
                storeMessage();
                JOptionPane.showMessageDialog(null, "Message successfully stored.");
                return "Message successfully stored.";
            default:
                return "No option selected.";
        }
    }

    // Display full message info
    public void printMessage() {
        JOptionPane.showMessageDialog(null,
                "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipient +
                        "\nMessage: " + messageText);
    }

    // Return total messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Store message in JSON file (ChatGPT generated method)
    @SuppressWarnings("unchecked")
    public void storeMessage() {
        JSONObject msgObj = new JSONObject();
        msgObj.put("MessageID", messageID);
        msgObj.put("Recipient", recipient);
        msgObj.put("Message", messageText);
        msgObj.put("MessageHash", messageHash);

        JSONArray msgList = new JSONArray();
        msgList.add(msgObj);

        try (FileWriter file = new FileWriter("storedMessages.json", true)) {
            file.write(msgList.toJSONString());
            file.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
        }
}
}
