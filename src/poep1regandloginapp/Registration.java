/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poep1regandloginapp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author RC_Student_lab
 */
public class Registration {
   public boolean checkUserName(String username){
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password){
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        
        for (char c : password.toCharArray()){
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }
    
    public boolean checkCellPhoneNumber(String cellPhone) {
        String regex = "^\\+27\\d{9,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellPhone);
        return matcher.matches();
    }
    
    public String registerUser(User user) {
        if (!checkUserName(user.getUsername())) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in legth.";
        }
        if (!checkPasswordComplexity(user.getPassword())){
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number and a special character ";
        }
        if (!checkCellPhoneNumber(user.getCellPhone())){
            return "Cell phone number is incorrectly formatted or does not contain international code.";
        }
        return "User has been registered successfully.";
    }
} 
