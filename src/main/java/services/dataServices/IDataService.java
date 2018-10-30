package services.dataServices;

import java.util.List;
import java.util.Map;

public interface IDataService {
    public String getOTP() throws Throwable;
    public String getOTP(String contractNo) throws Throwable;

    public String getContractByStatusAndType(String status, String contractType) throws Throwable;
    public String getCuidByContractNo(String contractNo) throws Throwable;
    public String getPrimaryPhoneByContractNo(String contract) throws Throwable;

    public boolean isAccountExisted(String phone) throws Throwable;

    public Map<String, Object> getInfoContractNotRegistered(String status, String contractType) throws Throwable;

    /******TUNT2*******/
    public List<Map<String, Object>> queryRandomPairOfPrimaryPhoneAndContract() throws Throwable;
}
