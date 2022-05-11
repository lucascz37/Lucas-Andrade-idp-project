package br.com.idp.lucas.project.features.quote;

import com.intuit.karate.junit5.Karate;

public class QuoteIT {

    @Karate.Test
    Karate testQuotes(){
        return Karate.run("quoteCreate", "quoteGet").relativeTo(getClass());
    }
}
