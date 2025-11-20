/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poep1regandloginapp;

import javax.swing.JOptionPane;
import java.util.Random;
import java.util.ArrayList;
import java.io.IOException;
import org.json.simple.JSONObject;

public class Messagee {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status; // SENT, STORED, DISREGARDED, NEW

    private static int totalMessagesSent = 0;
    private static ArrayList<Messagee> sentMessages = new ArrayList<>();

    // ===========================================================
    // CONSTRUCTORS
    // ===========================================================
    public Messagee() {
        this.messageID = generateMessageID();
        this.recipient = "";
        this.messageText = "";
        this.status = "NEW";
        this.messageHash = createMessageHash();
    }

    public Messagee(String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.status = "NEW";
        this.messageHash = createMessageHash();
    }

    public Messagee(String id, String recipient, String text, String hash, String status) {
        this.messageID = id;
        this.recipient = recipient;
        this.messageText = text;
        this.messageHash = hash;
        this.status = status == null ? "NEW" : status;
    }

    // ===========================================================
    // VALIDATION & HASH
    // ===========================================================
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) id.append(rand.nextInt(10));
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public boolean checkRecipientCell() {
        // +27 10 digits = 12 characters total
        return recipient != null && recipient.matches("^\\+27\\d{9,10}$");
    }

    public String checkMessageLength() {
        if (messageText == null || messageText.isEmpty())
            return "Message ready to send.";

        if (messageText.length() <= 250)
            return "Message ready to send.";

        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + ", please reduce size.";
    }

    public String createMessageHash() {
        String[] words = (messageText == null ? "" : messageText).trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 0 ? words[words.length - 1] : "";
        String prefix = messageID.substring(0, 2);
        this.messageHash = (prefix + ":" + totalMessagesSent + ":" + first + last).toUpperCase();
        return messageHash;
    }

    // ===========================================================
    // UI SEND OPTION
    // ===========================================================
    public String sendMessageOptionUI() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an action for this message:",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        return processSendChoice(choice);
    }

    // ===========================================================
    // NON-UI SEND OPTION FOR TESTING
    // ===========================================================
    public String sendMessageDirect(int option) {
        return processSendChoice(option);
    }

    private String processSendChoice(int option) {
        switch (option) {
            case 0: // SEND
                this.status = "SENT";
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";

            case 1: // DISREGARD
                this.status = "DISREGARDED";
                return "Message disregarded.";

            case 2: // STORE
                this.status = "STORED";
                storeMessageToFile();
                return "Message successfully stored.";

            default:
                return "No option selected.";
        }
    }

    // ===========================================================
    // JSON STORAGE
    // ===========================================================
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", messageID);
        obj.put("Recipient", recipient);
        obj.put("Message", messageText);
        obj.put("MessageHash", messageHash);
        obj.put("Status", status);
        return obj;
    }

    public static Messagee fromJSONObject(JSONObject json) {
        return new Messagee(
                (String) json.get("MessageID"),
                (String) json.get("Recipient"),
                (String) json.get("Message"),
                (String) json.get("MessageHash"),
                (String) json.get("Status")
        );
    }

    public void storeMessageToFile() {
        try {
            JsonStorage.appendMessage(this);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
        }
    }
    
    public void printMessage() {
    JOptionPane.showMessageDialog(null,
            "Message ID: " + messageID +
            "\nMessage Hash: " + messageHash +
            "\nRecipient: " + recipient +
            "\nMessage: " + messageText +
            "\nStatus: " + status
    );
}


    // ===========================================================
    // RUNTIME STATIC UTILITIES
    // ===========================================================
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static ArrayList<Messagee> getSentMessages() {
        return sentMessages;
    }

    public static void clearSentMessages() {
        sentMessages.clear();
        totalMessagesSent = 0;
    }

    // ===========================================================
    // GETTERS & SETTERS
    // ===========================================================
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }

    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
    public void setStatusDirect(String status) { this.status = status; }

    }

