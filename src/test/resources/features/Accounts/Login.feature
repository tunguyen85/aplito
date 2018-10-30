Feature: As a registered user of HCVN system
  I want to login to my account
  So that I can access to my info

  @login
  Scenario: login successful and save access_token to global_var
    Given Testcase-ID (BE_01) - Testcase description (Login successful and get access_token)
    Given endpoint ({endpoint.url.token)
    #Given authorization (basic;OpenApi:5763bfc30621487b980347cb6edf4c7a)
    Given param - string (header;Authorization;|basic_auth)
    And param - string (header;Content-Type;application/x-www-form-urlencoded)
    #Given content-type (URLENC)
    Given param - string (form;grant_type;password)
    And param - string (form;login_type;direct)
    And param - string (form;lang;vi)
    And param - string (form;username;@register.phone)
    And param - string (form;password;@password)
    When execute (POST)
    Then status (200)
    #And response contains (access_token)
    And extract response value (access_token) to (ACCESS_TOKEN)
    Then report - append note (logged in with username;@register.phone)
    And report - append note (access_token;?ACCESS_TOKEN)

  @demo1
  Scenario Outline: login with invalid info
    Given Testcase-ID (<testcase_ID>) - Testcase description (<test_desc>)
    Given endpoint (token)
    Given param - string (header;Authorization;|testdata.basic_auth)
    And param - string (header;Content-Type;application/x-www-form-urlencoded)
    Given param - string (form;grant_type;password)
    And param - string (form;login_type;direct)
    And param - string (form;lang;vi)
    And param - string (form;username;<username>)
    And param - string (form;password;<password>)
    And execute (POST)
    Then status (400)

    Examples:
      | testcase_ID | test_desc                                          | username  | password  |
      | BE_1        | Login with incorrect username & correct password   | incorrect | Qwer1234  |
      | BE_2        | Login with incorrect username & blank password     | incorrect |           |
      | BE_3        | Login with incorrect username & incorrect password | incorrect | incorrect |
      | BE_4        | Login with blank username & incorrect password     |           | incorrect |
      | BE_5        | Login with blank username & correct password       |           | Qwer1234  |
      | BE_6        | Login with blank username & blank password         |           |           |
      | BE_7        | Login with correct username & incorrect password   | correct   | incorrect |
      | BE_8        | Login with correct username & blank password       | correct   |           |