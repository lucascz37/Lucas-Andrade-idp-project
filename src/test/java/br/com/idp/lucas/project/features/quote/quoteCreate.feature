Feature: quoteCreate

  Background:
    * url baseUrl

  Scenario: create a quote

    * def createJson = karate.read('classpath:br/com/idp/lucas/project/features/quote/request/quoteCreate.json')
    * def createJsonResponse = karate.read('classpath:br/com/idp/lucas/project/features/quote/response/quoteCreate.json')

    Given path '/quote'
    When request createJson
    And method POST
    Then status 201
    And match response == createJsonResponse

  Scenario: create multiple quote existing

    * def createJson = karate.read('classpath:br/com/idp/lucas/project/features/quote/request/quoteCreateSecondQuote.json')

    Given path '/quote'
    When request createJson
    And method POST
    Then status 201

  Scenario: create multiple quote first time
    * def createJson = karate.read('classpath:br/com/idp/lucas/project/features/quote/request/quoteCreateMultipleFirstTime.json')
    * def createJsonResponse = karate.read('classpath:br/com/idp/lucas/project/features/quote/response/quoteCreateMultipleFirstTime.json')

    Given path '/quote'
    When request createJson
    And method POST
    Then status 201
    And match response == createJsonResponse

  Scenario: create quote with invalid value
    * def createJson = karate.read('classpath:br/com/idp/lucas/project/features/quote/request/quoteCreateInvalidQuoteValue.json')

    Given path '/quote'
    When request createJson
    And method POST
    Then status 400

  Scenario: create quote with invalid date
    * def createJson = karate.read('classpath:br/com/idp/lucas/project/features/quote/request/quoteCreateWrongDate.json')

    Given path '/quote'
    When request createJson
    And method POST
    Then status 400