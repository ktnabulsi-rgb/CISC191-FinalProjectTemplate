package edu.sdccd.cisc191.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogRequest {
    private Integer answer;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(LogRequest log) throws Exception {
        return objectMapper.writeValueAsString(log);
    }

    public static LogRequest fromJSON(String input) throws Exception {
        return objectMapper.readValue(input, LogRequest.class);
    }

    protected LogRequest() {
    }

    public LogRequest(Integer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return String.format(
                "Log[answer=%d]",
                answer);
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}