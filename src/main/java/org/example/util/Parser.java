package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TicketDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Parser {
    public List<TicketDto> parseTickets() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/tickets.json");

        if (!file.exists()) {
            throw new RuntimeException("Файл не найден: " + file.getAbsolutePath());
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode ticketsNode = rootNode.path("tickets");
            return objectMapper.convertValue(ticketsNode, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при парсинге файла tickets.json");
        }
    }
}
