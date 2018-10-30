package services.dataServices;

import dao.BslDaoImpl;
import dao.IBslDao;
import dao.ICSDao;
import dao.CSDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleDataService implements IDataService {

    private static IBslDao bslDao = new BslDaoImpl();
    private static ICSDao csDao = new CSDaoImpl();

    @Override
    public String getOTP() throws Throwable {
        try {
            return csDao.getOTP();
        } catch (Throwable e) {
            throw e;
        }
    }

    @Override
    public String getOTP(String contractNo) throws Throwable {
        try {
            return csDao.getOTP(contractNo);
        } catch (Throwable e) {
            throw e;
        }
    }

    @Override
    public String getContractByStatusAndType(String status, String contractType) throws Throwable {
        try{
            return bslDao.queryContracByStatusAndType(status,contractType);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public String getCuidByContractNo(String contractNo) throws Throwable {
        try{
            return bslDao.queryCuidFromContractNo(contractNo);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public String getPrimaryPhoneByContractNo(String contractNo) throws Throwable {
        try{
            String cuid = this.getCuidByContractNo(contractNo);
            return bslDao.queryPrimaryPhoneByCUID(cuid);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public boolean isAccountExisted(String phone) throws Throwable{
        try{
            if (phone.equals("")) return true;  //if phone is empty, that means the contract found in previous query
                                                //does not have primary mobile phone
                                                //so it is needed to rerun the previous query to get another contract
                                                //returning true here will make the previous query to be rerun
            return csDao.isAccountExisted(phone);   //if phone is not empty, then proceed to check its availability
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public Map<String, Object> getInfoContractNotRegistered(String status, String contractType) throws Throwable{
        String contractNo,cuid,phoneNo;
        Map<String, Object> ls = new HashMap<String,Object>();
        contractNo = bslDao.queryContracByStatusAndType(status, contractType);
        cuid = bslDao.queryCuidFromContractNo(contractNo);
        phoneNo = bslDao.queryPrimaryPhoneByCUID(cuid);

        while(isAccountExisted(phoneNo)){
            contractNo = bslDao.queryContracByStatusAndType(status, contractType);
            cuid = bslDao.queryCuidFromContractNo(contractNo);
            phoneNo = bslDao.queryPrimaryPhoneByCUID(cuid);
        }
        ls.put("phone",phoneNo);
        ls.put("contract",contractNo);
        ls.put("cuid",cuid);
        return  ls;
    }

    /******TUNT2*******/
    @Override
    public List<Map<String, Object>> queryRandomPairOfPrimaryPhoneAndContract() throws Throwable {
        try{
            return bslDao.queryRandomPairOfPrimaryPhoneAndContract();
        }catch (Throwable e){throw e;}
    }
}

