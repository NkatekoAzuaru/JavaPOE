/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poep1regandloginapp;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManagerrTest {

    private MessageManagerr manager;

    @BeforeMethod
    public void setup() {
        // Clear runtime messages before each test
        Messagee.getSentMessages().clear();
        manager = new MessageManagerr();
    }

    // ---------------------------------------------------------
    // TEST 1: Sender → Recipient list
    // ---------------------------------------------------------
    @Test
    public void testSenderRecipientList() {
        Messagee m = new Messagee("+27718693001", "Hello there");
        m.sendMessageDirect(0); // simulate SEND

        ArrayList<String> list = manager.getSenderRecipientList();

        assertFalse(list.isEmpty(), "List should not be empty");
        assertEquals(list.get(0), "You -> +27718693001");
    }

    // ---------------------------------------------------------
    // TEST 2: Longest Sent Message
    // ---------------------------------------------------------
    @Test
    public void testLongestMessage() {
        Messagee m1 = new Messagee("+27710001111", "Short message");
        Messagee m2 = new Messagee("+27710002222", "This is the longer message here!");

        m1.sendMessageDirect(0);
        m2.sendMessageDirect(0);

        String longest = manager.getLongestSentMessage();

        assertEquals(longest, "This is the longer message here!");
    }

    // ---------------------------------------------------------
    // TEST 3: Search by Message ID
    // ---------------------------------------------------------
    @Test
    public void testSearchByMessageID() {
        Messagee m = new Messagee("+27719998877", "Test search message");
        m.sendMessageDirect(0);

        String id = m.getMessageID();
        Messagee result = manager.searchByMessageID(id);

        assertNotNull(result, "Message should be found by ID");
        assertEquals(result.getMessageText(), "Test search message");
    }

    // ---------------------------------------------------------
    // TEST 4: Search messages by recipient
    // ---------------------------------------------------------
    @Test
    public void testSearchByRecipient() {
        Messagee m1 = new Messagee("+27715554444", "Message A");
        Messagee m2 = new Messagee("+27715554444", "Message B");

        m1.sendMessageDirect(0);
        m2.sendMessageDirect(0);

        ArrayList<String> result = manager.searchByRecipient("+27715554444");

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Message A"));
        assertTrue(result.contains("Message B"));
    }

    // ---------------------------------------------------------
    // TEST 5: Delete message by hash
    // ---------------------------------------------------------
    @Test
    public void testDeleteByHash() {
        Messagee m = new Messagee("+27718880000", "Delete this message");
        m.sendMessageDirect(0);

        String hash = m.getMessageHash();

        boolean deleted = manager.deleteByHash(hash);

        assertTrue(deleted, "Message should be deleted by hash");
        assertNull(manager.searchByMessageID(m.getMessageID()), "Message should not be found after deletion");
    }

    // ---------------------------------------------------------
    // TEST 6: Generate Report
    // ---------------------------------------------------------
    @Test
    public void testGenerateReport() {
        Messagee m = new Messagee("+27716667777", "Report test message");
        m.sendMessageDirect(0);

        String report = manager.generateReport();

        assertTrue(report.contains("Hash:"));
        assertTrue(report.contains("Recipient: +27716667777"));
        assertTrue(report.contains("Report test message"));
    }

    // ---------------------------------------------------------
    // TEST 7: No messages → Report returns "No sent messages."
    // ---------------------------------------------------------
    @Test
    public void testGenerateReport_NoMessages() {
        String report = manager.generateReport();
        assertEquals(report, "No sent messages.");
    }
}