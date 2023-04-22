package edu.sdccd.cisc191.template;

import static org.junit.jupiter.api.Assertions.*;

class HistoryResponseTest {
    private HistoryResponse historyResponse;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        historyResponse = new HistoryResponse(1, "Test", "User");
    }

    @org.junit.jupiter.api.Test
    void getCustomer() {
        assertEquals(historyResponse.toString(), "Customer[id=1, firstName='Test', lastName='User']");
    }

    @org.junit.jupiter.api.Test
    void setCustomer() {
        historyResponse.setFirstName("User");
        historyResponse.setLastName("Test");
        assertEquals(historyResponse.toString(), "Customer[id=1, firstName='User', lastName='Test']");
    }
}