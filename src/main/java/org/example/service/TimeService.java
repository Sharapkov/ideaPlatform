package org.example.service;


import org.example.dto.TicketDto;
import org.example.mapper.TicketMapper;
import org.example.model.Ticket;
import org.example.util.Parser;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimeService {
    private final TicketMapper ticketMapper;
    private final Parser parser;
    public TimeService(TicketMapper ticketMapper, Parser parser) {
        this.ticketMapper = ticketMapper;
        this.parser = parser;
    }

    public void fromVladivostokToTelAviv() {

        List<TicketDto> ticketsDto = parser.parseTickets();
        List<Ticket> tickets = ticketMapper.toListEntity(ticketsDto);
        Map<String, Duration> minFlightTimeMap = createMinFlightMap(tickets);

        minFlightTimeMap.forEach((carrier, minFlightTime) -> {
            long hours = minFlightTime.toHours();
            long minutes = minFlightTime.toMinutes() % 60;
            System.out.println("Перевозчик: " + carrier + ", Минимальное время полета: " + hours + " часов " + minutes + " минут");
        });

    }

    private Map<String, Duration> createMinFlightMap (List<Ticket> tickets) {
        Map<String, Duration> minFlightTimeMap = new HashMap<>();

        for(Ticket ticket: tickets) {
            if (!validateWay(ticket)) {
                continue;
            }

            String carrier = ticket.getCarrier();
            Duration duration = ticket.getFlight_time();

            minFlightTimeMap.computeIfPresent(carrier, (k, v) -> duration.compareTo(v) < 0 ? duration : v);
            minFlightTimeMap.computeIfAbsent(carrier, k -> duration);

        }
        return minFlightTimeMap;
    }

    private boolean validateWay(Ticket ticket) {
        return ticket.getOrigin_name().equals("Владивосток") && ticket.getDestination_name().equals("Тель-Авив");
    }

}
