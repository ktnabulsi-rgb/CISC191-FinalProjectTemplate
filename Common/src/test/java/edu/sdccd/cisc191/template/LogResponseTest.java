package edu.sdccd.cisc191.template;

import static org.junit.jupiter.api.Assertions.*;

class LogResponseTest {
    private LogResponse logResponse;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        logResponse = new LogResponse(1, "Test", "User");
    }

    @org.junit.jupiter.api.Test
    void getCustomer() {
        assertEquals(logResponse.toString(), "Customer[id=1, firstName='Test', lastName='User']");
    }

    @org.junit.jupiter.api.Test
    void setCustomer() {
        logResponse.setFirstName("User");
        logResponse.setLastName("Test");
        assertEquals(logResponse.toString(), "Customer[id=1, firstName='User', lastName='Test']");
    }
}