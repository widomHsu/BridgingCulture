package edu.monash.bridgingculture.service.entity.festival;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ChatBotHistory {

    String ip;
    String role;
    String content;
    Long time;
}
