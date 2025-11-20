/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poep1regandloginapp;

import javax.swing.JOptionPane;

/**
 * Main application (Part 1 + Part 2 + Part 3 menu integration)
 */
public class POEp1RegAndLoginApp {

    
    public static void main(String[] args) {
        Registration registration = new Registration();
        Login login = new Login();

        JOptionPane.showMessageDialog(null, "=== User Registration Form ===");

        String firstName = JOptionPane.showInputDialog("Please enter First name:");
        String lastName = JOptionPane.showInputDialog("Please enter Last name:");
        String username = JOptionPane.showInputDialog("Please enter username (include '_' and not more than 5 characters):");
        String password = JOptionPane.showInputDialog("Please enter password (8 characters, capital, number, special char):");
        String cellPhone = JOptionPane.showInputDialog("Please enter cell phone number (e.g. +27...)");

        User newUser = new User(username, password, cellPhone, firstName, lastName);
        String registrationMessage = registration.registerUser(newUser);
        JOptionPane.showMessageDialog(null, registrationMessage);

        if (!registrationMessage.equals("User has been registered successfully.")) {
            JOptionPane.showMessageDialog(null, "Please restart and try again.");
            return;
        }

        // LOGIN
        JOptionPane.showMessageDialog(null, "=== User Login ===");
        String loginUsername = JOptionPane.showInputDialog("Enter username:");
        String loginPassword = JOptionPane.showInputDialog("Enter password:");

        boolean loginStatus = login.loginUser(loginUsername, loginPassword, newUser);
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(loginStatus, newUser));

        if (!loginStatus) return;

        
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        MessageManagerr manager = new MessageManagerr();

        // Top-level menu (Part 2 + Part 3)
        String mainMenu = "Main Menu:\n"
                + "1. Send Messages\n"
                + "2. Message Management (Part 3 features)\n"
                + "3. Quit\n";

        while (true) {
            String choiceStr = JOptionPane.showInputDialog(mainMenu + "\nEnter option number:");
            if (choiceStr == null) break;
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number 1-3.");
                continue;
            }

            if (choice == 1) {
                // Send messages (same flow as Part 2)
                String totalStr = JOptionPane.showInputDialog("How many messages would you like to send?");
                if (totalStr == null) continue;
                int totalMessages;
                try {
                    totalMessages = Integer.parseInt(totalStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    continue;
                }

                for (int i = 0; i < totalMessages; i++) {
                    String recipient = JOptionPane.showInputDialog("Enter recipient cell number (+27 followed by 9 digits):");
                    String msgText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

                    Messagee msg = new Messagee(recipient, msgText);

                    // Validate recipient and message
                    if (!msg.checkRecipientCell()) {
                        JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        i--;
                        continue;
                    }

                    if (!msg.checkMessageLength().equals("Message ready to send.")) {
                        JOptionPane.showMessageDialog(null, msg.checkMessageLength());
                        i--;
                        continue;
                    }

                    // Choose send/store/disregard
                    msg.sendMessageOptionUI(); 

                    // Show message details
                    msg.printMessage();

                }
                JOptionPane.showMessageDialog(null, "Total messages sent (this run): " + Messagee.returnTotalMessages());
                // refresh manager arrays
                manager.refreshFromRuntime();

            } else if (choice == 2) {
                // Part 3 features
                manager.showPart3Menu();
            } else if (choice == 3) {
                JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid selection. Enter 1-3.");
            }
        }
    }
}
