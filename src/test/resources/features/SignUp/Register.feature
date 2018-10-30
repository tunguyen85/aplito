Feature: Register new account with a pair of contract number & phone number

  @register
  Scenario: Register account successfully with an existing user in BSL
    Given Testcase-ID (BE-01) - Testcase description (Successfully register an account existing in BSL)
    #Given query for a pair of contract number and phone number to register account - assign to (REG_CONTRACT) and (REG_PHONE)
    #step 1 - verify contract no and phone no
    Given endpoint ({endpoint.url.verify)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;@register.phone)
    And param - string (body;ContractNumber;@register.contract)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;0)
    #step 2 - query OTP and send OTP to api step 2
    Given query for OTP to register account with contract number = (@register.contract) and assign to global var (REG_OTP)
    Given endpoint ({endpoint.url.checkOtp)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;@register.phone)
    And param - string (body;ContractNumber;@register.contract)
    And param - string (body;VerificationCode;?REG_OTP)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;0)
    #step 3: set password
    Given endpoint ({endpoint.url.setPassword)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;@register.phone)
    And param - string (body;ContractNumber;@register.contract)
    And param - string (body;VerificationCode;?REG_OTP)
    And param - string (body;Password;@password)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;0)
    #add note to the final report
    Then report - append note (Registered account;@register.phone)
    And report - append note (Registered contract;@register.contract)

  @register1
  Scenario Outline: Register account unsuccessfully - failed at step 1
    Given Testcase-ID (<testcaseID>) - Testcase description (<test_desc>)
  #Given query for a pair of contract number and phone number to register account - assign to (REG_CONTRACT) and (REG_PHONE)
  #step 1 - verify contract no and phone no
    Given endpoint ({endpoint.url.verify)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;<phone>)
    And param - string (body;ContractNumber;<contract>)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;<response_code>)
    And verify response value equals (string;response_string_code;<response_string_code>)
    And verify response value equals (string;response_message;<response_message>)
    Examples:
      | testcaseID | test_desc                                                     | phone              | contract           | response_code | response_string_code                          | response_message                           |
      | BE_01      | Register account unsuccessfully with invalid phone            | #random_number(5)  | ?REG_CONTRACT      | 81            | ^register.response.invalidphone.stringcode    | ^register.response.invalidphone.message    |
      | BE_02      | Register account unsuccessfully with invalid contract         | ?REG_PHONE         | #random_number(10) | 70            | ^register.response.invalidcontract.stringcode | ^register.response.invalidcontract.message |
      | BE_03      | Register account unsuccessfully with invalid phone & contract | #random_number(10) | #random_number(10) | 77            | ^register.response.invalidphone.stringcode    | ^register.response.invalidphone.message    |
      | BE_04      | Register account unsuccessfully with registered phone         | @register.phone    | @register.contract | -99           | ^register.response.registeredphone.stringcode | ^register.response.registeredphone.message |
      | BE_05      | Register account unsuccessfully with empty phone              |                    | @register.contract | 13            | ^register.response.emptyphone.stringcode      | ^register.response.emptyphone.message      |
      | BE_06      | Register account unsuccessfully with empty contract           | @register.phone    |                    | 11            | ^register.response.emptycontract.stringcode   | ^register.response.emptycontract.message   |
      | BE_07      | Register account unsuccessfully with empty phone & contract   |                    |                    | 13            | ^register.response.emptyphone.stringcode      | ^register.response.emptyphone.message      |