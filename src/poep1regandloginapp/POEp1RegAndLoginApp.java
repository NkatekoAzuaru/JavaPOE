/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poep1regandloginapp;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
/**
 *
 * @author RC_Student_lab
 */
public class POEp1RegAndLoginApp {

    /**
     * @param args the command line arguments
     */
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

        // MENU
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        int totalMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));

        for (int i = 0; i < totalMessages; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient cell number (+27...)");
            String msgText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

            Message msg = new Message(recipient, msgText);

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
            msg.sendMessageOption();

            // Show message details
            msg.printMessage();
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());

        JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
    }
}

