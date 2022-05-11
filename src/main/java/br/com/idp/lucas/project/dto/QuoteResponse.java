package br.com.idp.lucas.project.dto;

import br.com.idp.lucas.project.entity.Quote;
import br.com.idp.lucas.project.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {

    private UUID id;

    private String stockId;

    private Map<LocalDate, String> quotes;

    public static QuoteResponse from(List<Quote> quotes){
        return QuoteResponse.generateQuoteResponse(quotes.get(0).getStock().getId(), quotes.get(0).getStock().getName(), quotes);
    }

    public static QuoteResponse from(Stock stock, Collection<Quote> quotes){
        return QuoteResponse.generateQuoteResponse(stock.getId(), stock.getName(), quotes);
    }

    private static QuoteResponse generateQuoteResponse(UUID id, String stockId, Collection<Quote> quotes){
        QuoteResponse response = new QuoteResponse();
        response.setId(id);
        response.setStockId(stockId);
        response.setQuotes(quotes.stream().collect(Collectors.toMap(Quote::getDate, Quote::getValue)));
        return response;
    }

}
