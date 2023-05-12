package edu.sdccd.cisc191.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.developer.Serialization;

public class LogResponse {
    private Boolean successful;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(LogResponse log) throws Exception {
        return objectMapper.writeValueAsString(log);
    }

    public static LogResponse fromJSON(String input) throws Exception {
        return objectMapper.readValue(input, LogResponse.class);
    }

    protected LogResponse() {
    }

    public LogResponse(boolean successful) {
        this.successful = true;
    }

    @Override
    public String toString() {
        return String.format("sucess[sucess=%b]", successful);
    }

}