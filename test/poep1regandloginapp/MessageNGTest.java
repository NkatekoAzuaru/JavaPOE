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
        Messagee msg = new Messagee("+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", msg.checkMessageLength(),
                "Expected message to be within 250 characters");
    }

    @Test
    public void testMessageTooLong() {
        // Generate a message longer than 250 chars
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 260; i++) longMsg.append("x");
        Messagee msg = new Messagee("+27718693002", longMsg.toString());
        String result = msg.checkMessageLength();
        assertTrue(result.contains("exceeds 250 characters"), 
                "Expected message length warning for exceeding 250 chars");
    }

    @Test
    public void testRecipientCellphoneCorrectFormat() {
        Messagee msg = new Messagee("+27718693002", "Hello!");
        assertTrue(msg.checkRecipientCell(), "Expected valid +27 cellphone format");
    }

    @Test
    public void testRecipientCellphoneIncorrectFormat() {
        Messagee msg = new Messagee("0812345678", "Hello!");
        assertFalse(msg.checkRecipientCell(), 
                "Expected invalid cellphone format (missing +27)");
    }

    @Test
    public void testCreateMessageHash() {
        Messagee msg = new Messagee("+27718693002", "Hi thanks");
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("^[0-9]{2}:[0-9]+:[A-Z]+[A-Z]+$"),
                "Expected correctly formatted message hash");
    }

    @Test
    public void testMessageIDLength() {
        Messagee msg = new Messagee("+27718693002", "Hi there");
        assertTrue(msg.checkMessageID(), 
                "Expected message ID to be 10 or fewer digits");
    }

   @Test
public void testSendMessageOption_Send() {
    Messagee msg = new Messagee("+27718693002", "Test message");

    // simulate choosing the SEND option (0)
    String result = msg.sendMessageDirect(0);

    assertNotNull(result, "Expected a result message after send/store/disregard");
    assertEquals("SENT", msg.getStatus(), "Message status should be SENT");
}

@Test
public void testTotalMessagesCount() {
    Messagee.clearSentMessages(); // reset static data before test

    int before = Messagee.returnTotalMessages();

    Messagee msg = new Messagee("+27718693002", "Hello again");

    msg.sendMessageDirect(0); // send message (option 0)

    int after = Messagee.returnTotalMessages();

    assertTrue(after == before + 1, "Expected total messages to increase by 1");
}
 
}
    

