package com.ogarose.popugjira.auth.service.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Message {
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("payload")
    private Object payload;
}
