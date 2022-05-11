Feature: quoteGet

  Background:
    * url baseUrl

  Scenario: get a quote

    * def createJsonResponse = karate.read('classpath:br/com/idp/lucas/project/features/quote/response/quoteCreateMultipleFirstTime.json')

    Given path '/quote/vale5'
    And method GET
    Then status 200
    And match response == createJsonResponse

  Scenario: get a quote that do not exists

    Given path '/quote/vale6'
    And method GET
    Then status 404

  Scenario: get quotes

    Given path '/quote'
    And method GET
    Then status 200
    And match response[0].id == '#uuid'