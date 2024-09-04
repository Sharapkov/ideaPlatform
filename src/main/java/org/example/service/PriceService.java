package org.example.service;

import org.example.dto.TicketDto;
import org.example.mapper.TicketMapper;
import org.example.model.Ticket;
import org.example.util.Parser;

import java.util.List;

public class PriceService {
    private final TicketMapper ticketMapper;
    private final Parser parser;
    public PriceService(TicketMapper ticketMapper, Parser parser) {
        this.ticketMapper = ticketMapper;
        this.parser = parser;
    }

    public void comparePrices() {

        List<TicketDto> ticketsDto = parser.parseTickets();
        List<Ticket> tickets = ticketMapper.toListEntity(ticketsDto);
        tickets.removeIf(ticket -> !ticket.getOrigin_name().equals("Владивосток")
                || !ticket.getDestination_name().equals("Тель-Авив"));

        long averagePrice = calculateAverage(tickets);
        long medianPrice = calculateMedian(tickets);
        printPriceDifference(averagePrice, medianPrice);

    }

    private long calculateAverage(List<Ticket> tickets) {
        long i = tickets.size();
        long sum = 0;
        for (Ticket ticket: tickets) {
            sum += ticket.getPrice();
        }
        return sum/i;
    }

    private long calculateMedian(List<Ticket> tickets) {
        List<Long> prices = tickets.stream()
                .mapToLong(Ticket::getPrice)
                .boxed()
                .sorted()
                .toList();

        int medianIndex = prices.size()/2;

        if (prices.size() % 2 == 0) {
            long median1 = prices.get(medianIndex);
            long median2 = prices.get(medianIndex-1);
            return (median1 + median2) / 2;
        } else return prices.get(medianIndex);
    }

    private void printPriceDifference(Long average, Long median) {
        if (average > median) {
            System.out.println("Разница между средней ценой и медианой для полета между городами Владивосток и Тель-Авив: " + (average - median));
        } else System.out.println("Разница между средней ценой и медианой для полета между городами Владивосток и Тель-Авив: " + (median - average));
    }


}
