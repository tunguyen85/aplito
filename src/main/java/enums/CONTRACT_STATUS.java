package enums;

public enum CONTRACT_STATUS {
    Approved("S"),
    Signed("N"),
    Active("A"),
    Rejected("D"),
    Cancelled("T"),
    Finished("K"),
    Paid_off("L"),
    Written_off("H");

    private String statusCode;

    CONTRACT_STATUS(String status) {
        this.statusCode = status;
    }


    public String getStatusCode() {
        return statusCode;
    }
}
