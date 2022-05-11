package br.com.idp.lucas.project.service;

import br.com.idp.lucas.project.dto.QuoteCreateRequest;
import br.com.idp.lucas.project.entity.Quote;
import br.com.idp.lucas.project.entity.Stock;
import br.com.idp.lucas.project.exception.ApiRequestException;
import br.com.idp.lucas.project.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> createFirstTime(QuoteCreateRequest request, Stock stock){
        return quoteRepository.saveAll(responseToQuote(request.getQuotes(), stock));
    }

    public List<Quote> create(QuoteCreateRequest request, Stock stock){
        List<Quote> quotes = quoteRepository.findByStockName(stock.getName());
        Map<String, String> newQuotes = request.getQuotes();
        var result = quotes.stream().filter(e -> newQuotes.containsKey(e.getDate().format(dateTimeFormatter))).collect(Collectors.toList());

        if(result.size() > 0){
            throw new ApiRequestException("Quote(s) already created", result);
        }

        List<Quote> allQuotes =  quoteRepository.saveAll(responseToQuote(newQuotes, stock));
        allQuotes.addAll(quotes);
        return allQuotes;
    }

    private List<Quote> responseToQuote(Map<String, String> request, Stock stock){
        return request.entrySet().stream().
                map((entry) -> Quote.builder()
                        .stock(stock)
                        .date(LocalDate.parse(entry.getKey(), dateTimeFormatter))
                        .value(entry.getValue()).build()).collect(Collectors.toList());
    }
}
