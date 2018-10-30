Feature: get list of contracts of a logged in user
  @contracts
  Scenario: successful
    Given Testcase-ID (BE-99) - Testcase description (Get contracts list successfully)
    Given Login with (@active_cel_account.username;@password) and get the access_token
    Given endpoint (api/customer/contracts)
    Given param - string (header;Authorization;?ACCESS_TOKEN)
    When execute (GET)
    Then status (200)
    And number of contracts is (|testdata.account.common.contracts.count)
    And work with response

