package edu.sdccd.cisc191.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryRequestTest {
    private HistoryRequest historyRequest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        historyRequest = new HistoryRequest(1);
    }

    @org.junit.jupiter.api.Test
    void getCustomer() {
        assertEquals(historyRequest.toString(), "Customer[id=1]");
    }

    @org.junit.jupiter.api.Test
    void setCustomer() {
        assertEquals(historyRequest.toString(), "Customer[id=1]");
    }
}