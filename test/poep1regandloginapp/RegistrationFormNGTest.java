/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poep1regandloginapp;

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_lab
 */
public class RegistrationFormNGTest {
    
    public RegistrationFormNGTest() {
    }
    
    RegistrationForm obj = new RegistrationForm();

    @Test
    public void testCheckUserName() {
        Assert.assertTrue(obj.checkUserName("kyl_1"));
        
    }

    @Test
    public void testCheckPasswordComplexity() {
        Assert.assert
    }

    @Test
    public void testCheckCellPhoneNumber() {
        Assert.assert
    }

    @Test
    public void testRegisterUser() {
        Assert.assert
    }
    
}
