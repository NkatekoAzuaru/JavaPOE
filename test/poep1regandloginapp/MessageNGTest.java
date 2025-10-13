/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poep1regandloginapp;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {
    
    @Test
    public void testMessageWithinLimit() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", msg.checkMessageLength(),
                "Expected message to be within 250 characters");
    }

    @Test
    public void testMessageTooLong() {
        // Generate a message longer than 250 chars
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 260; i++) longMsg.append("x");
        Message msg = new Message("+27718693002", longMsg.toString());
        String result = msg.checkMessageLength();
        assertTrue(result.contains("exceeds 250 characters"), 
                "Expected message length warning for exceeding 250 chars");
    }

    @Test
    public void testRecipientCellphoneCorrectFormat() {
        Message msg = new Message("+27718693002", "Hello!");
        assertTrue(msg.checkRecipientCell(), "Expected valid +27 cellphone format");
    }

    @Test
    public void testRecipientCellphoneIncorrectFormat() {
        Message msg = new Message("0812345678", "Hello!");
        assertFalse(msg.checkRecipientCell(), 
                "Expected invalid cellphone format (missing +27)");
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27718693002", "Hi thanks");
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("^[0-9]{2}:[0-9]+:[A-Z]+[A-Z]+$"),
                "Expected correctly formatted message hash");
    }

    @Test
    public void testMessageIDLength() {
        Message msg = new Message("+27718693002", "Hi there");
        assertTrue(msg.checkMessageID(), 
                "Expected message ID to be 10 or fewer digits");
    }

    @Test
    public void testSendMessageOption_Send() {
        Message msg = new Message("+27718693002", "Test message");
        String result = msg.sendMessageOption(); // Simulates user choosing option
        assertNotNull(result, "Expected a result message after send/store/disregard");
    }

    @Test
    public void testTotalMessagesCount() {
        int before = Message.returnTotalMessages();
        Message msg = new Message("+27718693002", "Hello again");
        msg.sendMessageOption();
        int after = Message.returnTotalMessages();
        assertTrue(after >= before, "Expected total messages sent to increment or stay equal");
    }
}

    