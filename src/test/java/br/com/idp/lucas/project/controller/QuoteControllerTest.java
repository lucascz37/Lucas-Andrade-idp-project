package br.com.idp.lucas.project.controller;

import br.com.idp.lucas.project.dto.QuoteCreateRequest;
import br.com.idp.lucas.project.dto.QuoteResponse;
import br.com.idp.lucas.project.entity.Quote;
import br.com.idp.lucas.project.entity.Stock;
import br.com.idp.lucas.project.service.QuoteService;
import br.com.idp.lucas.project.service.StockManagerService;
import br.com.idp.lucas.project.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {

    @Mock
    private QuoteService quoteService;

    @Mock
    private StockService stockService;

    @Mock
    private StockManagerService stockManagerService;

    private QuoteController quoteController;

    @BeforeEach
    void init(){
        quoteController = new QuoteController(quoteService, stockService, stockManagerService);
    }

    //services errors are not handled by the controller
    @Test
    void getQuoteExisting() {
        QuoteResponse response = new QuoteResponse();
        response.setStockId("petra4");
        Mockito.when(stockService.findOne("petra4")).thenReturn(response);
        ResponseEntity<?> entity = quoteController.getQuote("petra4");

        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertEquals(response, entity.getBody());
        Mockito.verify(stockService, Mockito.times(1)).findOne("petra4");
    }

    @Test
    void testGetQuotes() {
        QuoteResponse response = new QuoteResponse();
        response.setStockId("petra4");
        QuoteResponse response2 = new QuoteResponse();
        response2.setStockId("petra5");
        List<QuoteResponse> list = List.of(response, response2);

        Mockito.when(stockService.findAll()).thenReturn(list);
        ResponseEntity<?> entity = quoteController.getQuotes();

        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertEquals(list, entity.getBody());
        Mockito.verify(stockService, Mockito.times(1)).findAll();


    }

    @Test
    void createQuoteNotExisting() {
        QuoteCreateRequest request = new QuoteCreateRequest();
        request.setStockId("petra4");
        HashMap<String, String> quotes = new HashMap<>();
        quotes.put("2021-02-13", "10.2");
        quotes.put("2021-02-14", "10.22");
        request.setQuotes(quotes);

        Mockito.when(stockManagerService.getStocks()).thenReturn(Map.of());
        ResponseEntity<?> entity = quoteController.create(request);


        Assertions.assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
        Mockito.verify(stockManagerService, Mockito.times(1)).getStocks();

    }

    @Test
    void createQuoteExistingFirstTime() {
        QuoteCreateRequest request = new QuoteCreateRequest();
        request.setStockId("petra4");
        HashMap<String, String> quotes = new HashMap<>();
        quotes.put("2021-02-13", "10.2");
        quotes.put("2021-02-14", "10.22");
        request.setQuotes(quotes);

        Mockito.when(stockManagerService.getStocks()).thenReturn(Map.of("petra4", "nada aqui"));
        Mockito.when(stockService.getStock("petra4")).thenReturn(null);

        Stock stock = Stock.builder().name("petra4").build();
        Mockito.when(stockService.save("petra4")).thenReturn(stock);
        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.now());
        quote1.setValue("10.2");
        quote1.setStock(stock);
        Mockito.when(quoteService.createFirstTime(request, stock)).thenReturn(List.of(quote1));
        ResponseEntity<?> entity = quoteController.create(request);


        Assertions.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        Mockito.verify(stockManagerService, Mockito.times(1)).getStocks();
        Mockito.verify(quoteService, Mockito.times(1)).createFirstTime(request, stock);
        Mockito.verify(stockService, Mockito.times(1)).save("petra4");

    }

    @Test
    void createQuoteExistingMoreThaOnce() {
        QuoteCreateRequest request = new QuoteCreateRequest();
        request.setStockId("petra4");
        HashMap<String, String> quotes = new HashMap<>();
        quotes.put("2021-02-13", "10.2");
        quotes.put("2021-02-14", "10.22");
        request.setQuotes(quotes);

        Mockito.when(stockManagerService.getStocks()).thenReturn(Map.of("petra4", "nada aqui"));
        Stock stock = Stock.builder().name("petra4").build();
        Mockito.when(stockService.getStock("petra4")).thenReturn(stock);
        Quote quote1 = new Quote();
        quote1.setDate(LocalDate.now());
        quote1.setValue("10.2");
        quote1.setStock(stock);
        Mockito.when(quoteService.create(request, stock)).thenReturn(List.of(quote1));
        ResponseEntity<?> entity = quoteController.create(request);


        Assertions.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        Mockito.verify(stockManagerService, Mockito.times(1)).getStocks();
        Mockito.verify(quoteService, Mockito.times(1)).create(request, stock);
        Mockito.verify(quoteService, Mockito.times(0)).createFirstTime(request, stock);
        Mockito.verify(stockService, Mockito.times(0)).save("petra4");

    }
}