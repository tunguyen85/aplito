package dao;

public interface ICSDao {
    public String getOTP(String contractNo) throws Throwable;
    public String getOTP() throws Throwable;
    public String getCUIDbyUsername(String username) throws Throwable;

    public boolean isAccountExisted(String phone);
}
