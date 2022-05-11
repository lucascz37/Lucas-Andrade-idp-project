package br.com.idp.lucas.project.service;

import br.com.idp.lucas.project.dto.QuoteResponse;
import br.com.idp.lucas.project.entity.Stock;
import br.com.idp.lucas.project.exception.ApiNotFoundException;
import br.com.idp.lucas.project.repository.QuoteRepository;
import br.com.idp.lucas.project.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepository;

    private final QuoteRepository quoteRepository;

    public StockService(StockRepository stockRepository, QuoteRepository quoteRepository) {
        this.stockRepository = stockRepository;
        this.quoteRepository = quoteRepository;
    }

    public Stock getStock(String stock){
        return stockRepository.findByName(stock);
    }

    public Stock save(String stockName){
        return stockRepository.save(Stock.builder().name(stockName.toLowerCase(Locale.ROOT)).build());
    }

    public List<QuoteResponse> findAll(){
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(e -> QuoteResponse.from(e, quoteRepository.findByStockName(e.getName()))).collect(Collectors.toList());
    }

    public QuoteResponse findOne(String stockId){
        try {
            return QuoteResponse.from(stockRepository.findByName(stockId), quoteRepository.findByStockName(stockId));
        }catch (NullPointerException ignored){
            throw new ApiNotFoundException("invalid stockId " + stockId, null);
        }
    }

}
