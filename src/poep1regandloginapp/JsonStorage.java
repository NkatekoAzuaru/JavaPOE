/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author RC_Student_Lab
 */
package poep1regandloginapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonStorage {

    private static final String FILE_NAME = "storedMessages.json";

    // ---------------------------------------
    // READ ALL MESSAGES FROM JSON FILE
    // ---------------------------------------
    public static ArrayList<Messagee> readAllMessages() {
        ArrayList<Messagee> messages = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(FILE_NAME)) {
            Object obj = parser.parse(reader);
            JSONArray list = (JSONArray) obj;

            for (Object o : list) {
                JSONObject jsonMsg = (JSONObject) o;
                Messagee m = new Messagee();

                m.setMessageID((String) jsonMsg.get("MessageID"));
                m.setRecipient((String) jsonMsg.get("Recipient"));
                m.setMessageText((String) jsonMsg.get("Message"));
                m.setMessageHash((String) jsonMsg.get("MessageHash"));
                m.setStatusDirect((String) jsonMsg.get("Status"));

                messages.add(m);
            }

        } catch (IOException | ParseException e) {
            // File not found or empty → return empty list
        }

        return messages;
    }


    // ---------------------------------------
    // APPEND A MESSAGE TO JSON FILE
    // ---------------------------------------
    @SuppressWarnings("unchecked")
    public static void appendMessage(Messagee message) throws IOException {

        // Load existing messages
        ArrayList<Messagee> current = readAllMessages();
        JSONArray list = new JSONArray();

        // Rebuild JSON array
        for (Messagee m : current) {
            JSONObject obj = new JSONObject();
            obj.put("MessageID", m.getMessageID());
            obj.put("Recipient", m.getRecipient());
            obj.put("Message", m.getMessageText());
            obj.put("MessageHash", m.getMessageHash());
            obj.put("Status", m.getStatus());
            list.add(obj);
        }

        // Add the new message
        JSONObject newMsg = new JSONObject();
        newMsg.put("MessageID", message.getMessageID());
        newMsg.put("Recipient", message.getRecipient());
        newMsg.put("Message", message.getMessageText());
        newMsg.put("MessageHash", message.getMessageHash());
        newMsg.put("Status", message.getStatus());

        list.add(newMsg);

        // Write back to file
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(list.toJSONString());
            writer.flush();
        }
    }


    // ---------------------------------------
    // DELETE MESSAGE BY HASH
    // ---------------------------------------
    public static boolean deleteByHash(String hash) throws IOException {

        ArrayList<Messagee> current = readAllMessages();
        boolean removed = false;

        JSONArray updatedList = new JSONArray();

        for (Messagee m : current) {
            if (m.getMessageHash().equalsIgnoreCase(hash)) {
                removed = true;
                continue;  // skip (delete)
            }

            // Keep message
            JSONObject obj = new JSONObject();
            obj.put("MessageID", m.getMessageID());
            obj.put("Recipient", m.getRecipient());
            obj.put("Message", m.getMessageText());
            obj.put("MessageHash", m.getMessageHash());
            obj.put("Status", m.getStatus());
            updatedList.add(obj);
        }

        // Save updated list
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(updatedList.toJSONString());
            writer.flush();
        }

        return removed;
    }
    }

