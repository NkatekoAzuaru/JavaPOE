/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poep1regandloginapp;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.testng.Assert;
/**
 *
 * @author RC_Student_lab
 */
public class LoginNGTest {
    
    Login obj = new Login();

    @Test
    public void testCheckUserNameValid() {
        Assert.assertTrue(obj.checkUserName("kyl_1"));
        
    }
    @Test
    public void testCheckUserNameInvalid() {
        Assert.assertFalse(obj.checkUserName("kyle!!!!!!!"));
    }
    
    @Test
    public void testCheckPasswordComplexityValid() {
        Assert.assertTrue(obj.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
     @Test
    public void testCheckPasswordComplexityInvalid() {
        Assert.assertFalse(obj.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumberValid() {
        Assert.assertTrue(obj.checkCellPhoneNumber("+27838968979"));
    }
    
    @Test
    public void testCheckCellPhoneNumberInvalid() {
        Assert.assertFalse(obj.checkCellPhoneNumber("08966553"));
    }
}