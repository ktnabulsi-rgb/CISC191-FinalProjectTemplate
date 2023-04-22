package edu.sdccd.cisc191.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HistoryRequest {
    private Integer id;

    public static final String HR = "History Request";

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(HistoryRequest customer) throws Exception {
        return objectMapper.writeValueAsString(customer);
    }
    public static HistoryRequest fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, HistoryRequest.class);
    }
    protected HistoryRequest() {}

    public HistoryRequest(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d]",
                id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}