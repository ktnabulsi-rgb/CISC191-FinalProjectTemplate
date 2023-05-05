package edu.sdccd.cisc191.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogRequestTest {
    private LogRequest logRequest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        logRequest = new LogRequest(1);
    }

    @org.junit.jupiter.api.Test
    void getCustomer() {
        assertEquals(logRequest.toString(), "Customer[id=1]");
    }

    @org.junit.jupiter.api.Test
    void setCustomer() {
        assertEquals(logRequest.toString(), "Customer[id=1]");
    }
}