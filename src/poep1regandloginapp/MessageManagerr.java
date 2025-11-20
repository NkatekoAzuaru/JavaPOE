/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poep1regandloginapp;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Comparator;
/**
 *
 * @author RC_Student_Lab
 */
public class MessageManagerr {
   private ArrayList<Messagee> sentMessages = new ArrayList<>();
    private ArrayList<Messagee> disregardedMessages = new ArrayList<>();
    private ArrayList<Messagee> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();

    public MessageManagerr() {
        refreshFromRuntime();
    }

    // refresh arrays from runtime (Message.getSentMessages()) and stored file
    public void refreshFromRuntime() {
        sentMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        // runtime sent messages
        sentMessages.addAll(Messagee.getSentMessages());
        for (Messagee m : sentMessages) {
            messageHashes.add(m.getMessageHash());
            messageIDs.add(m.getMessageID());
        }
        // load stored messages from file
        storedMessages = JsonStorage.readAllMessages();
        for (Messagee m : storedMessages) {
            if (m.getMessageHash() != null) messageHashes.add(m.getMessageHash());
            if (m.getMessageID() != null) messageIDs.add(m.getMessageID());
        }
        // disregarded messages are those with status DISREGARDED in runtime
        for (Messagee m : Messagee.getSentMessages()) {
            if ("DISREGARDED".equalsIgnoreCase(m.getStatus())) disregardedMessages.add(m);
        }
    }

    // Non-UI: return sender-recipient list (sender is "You")
    public ArrayList<String> getSenderRecipientList() {
        refreshFromRuntime();
        ArrayList<String> result = new ArrayList<>();
        for (Messagee m : sentMessages) result.add("You -> " + m.getRecipient());
        return result;
    }

    // Non-UI: return longest sent message text (null if none)
    public String getLongestSentMessage() {
        refreshFromRuntime();
        if (sentMessages.isEmpty()) return null;
        return sentMessages.stream()
                .max(Comparator.comparingInt(x -> x.getMessageText().length()))
                .get()
                .getMessageText();
    }

    // Non-UI: search for message by ID, return message text (null if not found)
    public Messagee searchByMessageID(String id) {
        refreshFromRuntime();
        for (Messagee m : sentMessages) if (m.getMessageID().equals(id)) return m;
        for (Messagee m : storedMessages) if (m.getMessageID().equals(id)) return m;
        return null;
    }

    // Non-UI: get all messages (texts) for a recipient
    public ArrayList<String> searchByRecipient(String recipient) {
        refreshFromRuntime();
        ArrayList<String> results = new ArrayList<>();
        for (Messagee m : sentMessages) if (m.getRecipient().equals(recipient)) results.add(m.getMessageText());
        for (Messagee m : storedMessages) if (m.getRecipient().equals(recipient)) results.add(m.getMessageText());
        return results;
    }

    // Non-UI: delete message by hash from both in-memory sent list and JSON file
    public boolean deleteByHash(String hash) {
        refreshFromRuntime();
        // remove from in-memory sentMessages (and runtime list)
        Messagee toRemove = null;
        for (Messagee m : sentMessages) {
            if (m.getMessageHash() != null && m.getMessageHash().equalsIgnoreCase(hash)) {
                toRemove = m;
                break;
            }
        }
        if (toRemove != null) {
            sentMessages.remove(toRemove);
            Messagee.getSentMessages().remove(toRemove);
        }
        // remove from JSON file (if present)
        try {
            boolean removedFromFile = JsonStorage.deleteByHash(hash);
            return removedFromFile || (toRemove != null);
        } catch (Exception e) {
            return toRemove != null;
        }
    }

    // Non-UI: generate report string listing all sent messages (hash, recipient, message)
    public String generateReport() {
        refreshFromRuntime();
        if (sentMessages.isEmpty()) return "No sent messages.";
        StringBuilder sb = new StringBuilder();
        for (Messagee m : sentMessages) {
            sb.append("Hash: ").append(m.getMessageHash()).append("\n");
            sb.append("Recipient: ").append(m.getRecipient()).append("\n");
            sb.append("Message: ").append(m.getMessageText()).append("\n");
            sb.append("-------------------------\n");
        }
        return sb.toString();
    }

    // UI wrapper (unchanged) — calls non-UI methods
    public void showPart3Menu() {
        String menu = "Part 3 - Message Arrays & Report\n"
                + "1. Display sender & recipient of sent messages\n"
                + "2. Display longest sent message\n"
                + "3. Search by Message ID\n"
                + "4. Search messages by Recipient\n"
                + "5. Delete message by Hash\n"
                + "6. Display full report of sent messages\n"
                + "7. Load stored messages from JSON file\n"
                + "8. Back to main menu\n";
        while (true) {
            String choiceStr = JOptionPane.showInputDialog(menu + "\nEnter option number:");
            if (choiceStr == null) return;
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                continue;
            }
            switch (choice) {
                case 1 -> JOptionPane.showMessageDialog(null, String.join("\n", getSenderRecipientList()));
                case 2 -> JOptionPane.showMessageDialog(null, getLongestSentMessage() == null ? "No sent messages." : getLongestSentMessage());
                case 3 -> {
                    String id = JOptionPane.showInputDialog("Enter Message ID:");
                    Messagee found = searchByMessageID(id);
                    JOptionPane.showMessageDialog(null, found == null ? "Not found." : ("Recipient: " + found.getRecipient() + "\nMessage: " + found.getMessageText()));
                }
                case 4 -> {
                    String r = JOptionPane.showInputDialog("Enter recipient:");
                    JOptionPane.showMessageDialog(null, String.join("\n", searchByRecipient(r)));
                }
                case 5 -> {
                    String h = JOptionPane.showInputDialog("Enter message hash to delete:");
                    boolean ok = deleteByHash(h);
                    JOptionPane.showMessageDialog(null, ok ? "Deleted." : "Not found.");
                }
                case 6 -> JOptionPane.showMessageDialog(null, generateReport());
                case 7 -> {
                    storedMessages = JsonStorage.readAllMessages();
                    JOptionPane.showMessageDialog(null, "Stored messages loaded: " + storedMessages.size());
                }
                case 8 -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
}
  
