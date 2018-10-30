Feature: login

  @123
  Scenario Outline: demo login
    Given Testcase-ID (BE-9999) - Testcase description (login cho vui)
    Given endpoint (token)
    Given param - string (header;Content-Type;application/x-www-form-urlencoded)
    And param - string (header;Authorization;@basic_auth)
    Given param - string (form;grant_type;password)
    And param - string (form;username;<phone>)
    And param - string (form;password;Qwer1234)
    And param - string (form;login_type;direct)
    When execute (POST)
    Then status (200)
    Examples:
      | phone      |
      | 0934313309 |
