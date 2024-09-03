package org.example.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class Ticket {
    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    private LocalDateTime departure_date_time;
    private LocalDateTime arrival_date_time;
    private Duration flight_time;
    private String carrier;
    private String stops;
    private Long price;
}
