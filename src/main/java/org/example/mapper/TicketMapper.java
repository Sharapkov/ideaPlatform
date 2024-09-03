package org.example.mapper;

import org.example.dto.TicketDto;
import org.example.model.Ticket;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TicketMapper {
    public static Ticket toEntity(TicketDto dto) {
        Ticket entity = new Ticket();

        if (dto.getOrigin() != null) {
            entity.setOrigin(dto.getOrigin());
        }
        if (dto.getOrigin_name() != null) {
            entity.setOrigin_name(dto.getOrigin_name());
        }
        if (dto.getDestination() != null) {
            entity.setDestination(dto.getDestination());
        }
        if (dto.getDestination_name() != null) {
            entity.setDestination_name(dto.getDestination_name());
        }

        if (dto.getDeparture_date() != null && dto.getDeparture_time() != null) {
            entity.setDeparture_date_time(convertDate(dto.getDeparture_date(), dto.getDeparture_time()));
        }

        if (dto.getArrival_date() != null && dto.getArrival_time() != null) {
            entity.setArrival_date_time(convertDate(dto.getArrival_date(), dto.getArrival_time()));
        }

        if (entity.getArrival_date_time() != null && entity.getDeparture_date_time() != null) {
            entity.setFlight_time(Duration.between(entity.getDeparture_date_time(), entity.getArrival_date_time()));
        }

        if (dto.getCarrier() != null) {
            entity.setCarrier(dto.getCarrier());
        }
        if (dto.getStops() != null) {
            entity.setStops((dto.getStops()));
        }
        if (dto.getPrice() != null) {
            entity.setPrice(Long.valueOf(dto.getPrice()));
        }

        return entity;
    }

    public List<Ticket> toListEntity(List<TicketDto> list) {
        return list.stream()
                .map(TicketMapper::toEntity)
                .collect(Collectors.toList());
    }

    private static LocalDateTime convertDate(String localDate, String localTime) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (localTime.length() < 5) {
            String str = "0";
            localTime = str.concat(localTime);
        }

        LocalDate date = LocalDate.parse(localDate, dateFormatter);
        LocalTime time = LocalTime.parse(localTime, timeFormatter);

        return LocalDateTime.of(date, time);
    }
}
