package com.ogarose.popugjira.infrastructure.messaging.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IncomeMessage {
    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("payload")
    private JsonNode payload;
}
