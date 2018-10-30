Feature: prepare test data before executing test

  @prepare_data
  Scenario Outline: add account which matches the given criteria to testdata file
    Given Testcase-ID (<testcaseID>) - Testcase description (<test_desc>)
    Given query for client that has (<contract_status>) (<contract_type>) contract, and extract data to (<var_phone>) and (<var_contract>)
    And register account with (?<var_phone>) and (?<var_contract>)
    Then add to test data (<prop_username>;?<var_phone>)
    And add to test data (<prop_contract>;?<var_contract>)
    And update test data file and reload it to Environment.testdata
    And report - append note (Username;?<var_phone>)
    And report - append note (Contract number;?<var_contract>)

    Examples:
      | testcaseID | test_desc                                                                   | contract_status | contract_type | var_phone    | var_contract    | prop_username                  | prop_contract                     |
      | DATA01     | Register account which has Active CEL contract and add to test data file    | Active          | CEL           | DATA01_PHONE | DATA01_CONTRACT | active_cel_account.username    | active_cel_account.contract_no    |
      | DATA02     | Register account which has Active REL contract and add to test data file    | Active          | REL           | DATA02_PHONE | DATA02_CONTRACT | active_rel_account.username    | active_rel_account.contract_no    |
      | DATA03     | Register account which has Signed CEL contract and add to test data file    | Signed          | CEL           | DATA03_PHONE | DATA03_CONTRACT | signed_cel_account.username    | signed_cel_account.contract_no    |
      | DATA04     | Register account which has Signed REL contract and add to test data file    | Signed          | REL           | DATA04_PHONE | DATA04_CONTRACT | signed_rel_account.username    | signed_rel_account.contract_no    |
      | DATA05     | Register account which has Approved CEL contract and add to test data file  | Approved        | CEL           | DATA05_PHONE | DATA05_CONTRACT | approved_cel_account.username  | approved_cel_account.contract_no  |
      | DATA06     | Register account which has Approved REL contract and add to test data file  | Approved        | REL           | DATA06_PHONE | DATA06_CONTRACT | approved_rel_account.username  | approved_rel_account.contract_no  |
      | DATA07     | Register account which has Finished CEL contract and add to test data file  | Finished        | CEL           | DATA07_PHONE | DATA07_CONTRACT | finished_cel_account.username  | finished_cel_account.contract_no  |
      | DATA08     | Register account which has Finished REL contract and add to test data file  | Finished        | REL           | DATA08_PHONE | DATA08_CONTRACT | finished_rel_account.username  | finished_rel_account.contract_no  |
      | DATA09     | Register account which has Cancelled CEL contract and add to test data file | Cancelled       | CEL           | DATA09_PHONE | DATA09_CONTRACT | cancelled_cel_account.username | cancelled_cel_account.contract_no |
      | DATA10     | Register account which has Cancelled REL contract and add to test data file | Cancelled       | REL           | DATA10_PHONE | DATA10_CONTRACT | cancelled_rel_account.username | cancelled_rel_account.contract_no |
      | DATA11     | Register account which has Rejected CEL contract and add to test data file  | Rejected        | CEL           | DATA11_PHONE | DATA11_CONTRACT | rejected_cel_account.username  | rejected_cel_account.contract_no  |
      | DATA12     | Register account which has Rejected REL contract and add to test data file  | Rejected        | REL           | DATA12_PHONE | DATA12_CONTRACT | rejected_rel_account.username  | rejected_rel_account.contract_no  |


  @prepare_data1
    Scenario: Get customer info to register
      Given Testcase-ID (Test_data1) - Testcase description (Get customer info from BSL)
      Given get and store contract info not registered (Active) (CEL) contract, and extract data to (register.phone) (register.contract) (register.cuid)
      Then update test data file and reload it to Environment.testdata