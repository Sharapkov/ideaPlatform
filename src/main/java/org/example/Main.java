package org.example;

import org.example.mapper.TicketMapper;
import org.example.service.PriceService;
import org.example.service.TimeService;
import org.example.util.Parser;

public class Main {

    public static void main(String[] args) {

        TicketMapper ticketMapper = new TicketMapper();
        Parser parser = new Parser();
        TimeService timeService = new TimeService(ticketMapper, parser);
        PriceService priceService = new PriceService(ticketMapper, parser);

        timeService.fromVladivostokToTelAviv();
        System.out.println();
        priceService.comparePrices();

    }
}