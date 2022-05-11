package br.com.idp.lucas.project.controller;

import br.com.idp.lucas.project.dto.InvalidStockResponse;
import br.com.idp.lucas.project.dto.QuoteCreateRequest;
import br.com.idp.lucas.project.dto.QuoteResponse;
import br.com.idp.lucas.project.entity.Quote;
import br.com.idp.lucas.project.entity.Stock;
import br.com.idp.lucas.project.service.StockManagerService;
import br.com.idp.lucas.project.service.QuoteService;
import br.com.idp.lucas.project.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    private final StockService stockService;
    private final StockManagerService stockManagerService;

    public QuoteController(QuoteService quoteService, StockService stockService, StockManagerService stockManagerService) {
        this.quoteService = quoteService;
        this.stockService = stockService;
        this.stockManagerService = stockManagerService;
    }

    @GetMapping
    public ResponseEntity<?> getQuotes(){
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<?> getQuote(@PathVariable String stockId){
        return ResponseEntity.ok(stockService.findOne(stockId.toLowerCase(Locale.ROOT)));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody QuoteCreateRequest request){

        request.setStockId(request.getStockId().toLowerCase(Locale.ROOT));

        if(stockManagerService.getStocks().containsKey(request.getStockId())){
            Stock stock = stockService.getStock(request.getStockId());
            List<Quote> quotes;

            if(stock == null){
                stock = stockService.save(request.getStockId());
                quotes = quoteService.createFirstTime(request, stock);
            }else{
               quotes = quoteService.create(request, stock);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(QuoteResponse.from(quotes));

        }

        return ResponseEntity.badRequest().body(new InvalidStockResponse(request.getStockId() + " is not valid!"));
    }
}
