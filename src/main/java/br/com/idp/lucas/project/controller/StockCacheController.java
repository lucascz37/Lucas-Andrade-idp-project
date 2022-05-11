package br.com.idp.lucas.project.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockcache")
public class StockCacheController {

    @DeleteMapping
    @CacheEvict(value = "stocks")
    public void get(){
    }
}
