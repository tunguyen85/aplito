Feature: register for a new account with a pair of contract number & phone number

  @register_batch
  Scenario Outline: register account successfully
    Given Testcase-ID (BE-77) - Testcase description (Successfully register an account)
    #Given query for a pair of contract number and phone number to register account - assign to (REG_CONTRACT) and (REG_PHONE)
    #step 1 - verify contract no and phone no
    Given endpoint (api/customer/signup/verify)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;<phone>)
    And param - string (body;ContractNumber;<contract>)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;0)
    #step 2 - query OTP and send OTP to api step 2
    Given query for OTP to register account with contract number = (<contract>) and assign to global var (REG_OTP)
    Given endpoint (api/customer/signup/verify/otp)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;<phone>)
    And param - string (body;ContractNumber;<contract>)
    And param - string (body;VerificationCode;?REG_OTP)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;0)
    #step 3: set password
    Given endpoint (api/customer/signup)
    Given param - string (header;Content-Type;application/json)
    And param - string (body;PhoneNumber;<phone>)
    And param - string (body;ContractNumber;<contract>)
    And param - string (body;VerificationCode;?REG_OTP)
    And param - string (body;Password;|testdata.password)
    When execute (POST)
    Then status (200)
    And verify response value equals (int;response_code;0)
    #add note to the final report
    Then report - append note (Registered account;<phone>)
    And report - append note (Registered contract;<contract>)

    Examples:
      | phone    | contract |
      |0939515978|3802838735|
      |0700754291|3802838651|
      |0225216220|3802838576|
      |0932107385|3802838561|
      |0932107385|3802838525|
      |0277733715|3802838489|
      |0976038465|3802838427|
      |0831333635|3802838706|
      |0932107385|3802838557|
      |0277733715|3802838489|



