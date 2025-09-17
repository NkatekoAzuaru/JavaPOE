/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poep1regandloginapp;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author RC_Student_lab
 */
public class POEp1RegAndLoginApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        RegistrationForm registration = new RegistrationForm();
        LoginForm login = new LoginForm();
        
        System.out.println("=== User Registration Form ===");
        System.out.println("Please enter First name: ");
        String firstName = scanner.nextLine();
        
        System.out.println("Please enter Last name: ");
        String lastName = scanner.nextLine();
        
        System.out.println("Please enter username: NB. include: (_ & not more than 5 characters)");
        String username = scanner.nextLine();
        
        System.out.println("Please enter password: NB. 8 Characters, Capital letter, contain a number & special character");
        String password = scanner.nextLine();
        
        System.out.println("Please enter cell phone number (e.g. +27): ");
        String cellPhone = scanner.nextLine();
        
        User newUser = new User(username, password, cellPhone, firstName, lastName);
        String registrationMessage = registration.registerUser(newUser);
        System.out.println(registrationMessage);
        
        if (!registrationMessage.equals("User has been registered successfully.")){
            System.out.println("Please restart and try again.");
            return;
        }
        //Login Steps
        System.out.println("\n=== User Login ===");
        System.out.println("Enter username: ");
        String loginUsername = scanner.nextLine();
        
        System.out.println("Enter password: ");
        String loginPassword = scanner.nextLine();
        
        boolean loginStatus = login.loginUser(loginUsername, loginPassword, newUser);
        System.out.println(login.returnLoginStatus(loginStatus, newUser));
        
        scanner.close();
    }
    
}

