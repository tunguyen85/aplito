package enums;

public enum ERROR_CODE {
    E01("Response status code is not as expected."),
    E02("Response message is not as expected."),
    E03("Response body does not contains the expected key."),
    E04("Response body does not match expected value."),
    E05("Response data does not match expected value."),
    E10("No result found for query statement."),
    E20("OTP error - while processing OTP for registration."),
    E30("Account error - while logging in with given username and password."),
    E31("Account error - while registering account with given phone and contract_no."),
    E95("Error while processing resource file"),
    E96("Error while processing settings file"),
    E97("Error while processing environment file"),
    E98("Error while processing testdata file"),
    E99("Unexpected error.")
    ;

    private String errorMessage;

    ERROR_CODE(String errorMessage) {this.errorMessage = errorMessage;}

    public String getErrorMessage() { return this.errorMessage; }
}
