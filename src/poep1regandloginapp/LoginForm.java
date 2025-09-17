/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poep1regandloginapp;

/**
 *
 * @author RC_Student_lab
 */
public class LoginForm {
  public boolean loginUser(String username, String password, User user){
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }
    
    public String returnLoginStatus(boolean loginStatus, User user) {
        if (loginStatus) {
            return "Welcome " + user.getFisrtName() + " " + user.getLastName() + ", it is great to see you again.";
        }else{
            return "Username or password incorrct, please try again.";
        }
    }
}  
