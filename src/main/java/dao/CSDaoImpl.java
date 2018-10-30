package dao;

import org.springframework.jdbc.core.JdbcTemplate;
import utilities.Environment;
import utilities.Log;

import java.util.List;

public class CSDaoImpl implements ICSDao {
    private JdbcTemplate jdbcTemplate;

    public CSDaoImpl(){
        this.jdbcTemplate = Environment.dataSources.getCsJdbcTemplate();
    }

    @Override
    public String getOTP(String contractNo) throws Throwable {
        String query = "SELECT code FROM CS_CODE_REQUESTS CCR"
                        + " WHERE CCR.CONTACTID = (SELECT CUC.ID FROM CS_USER_CONTACT CUC"
                        + " WHERE CUC.IDUSER = (SELECT CU.ID FROM CS_USER CU"
                        + " WHERE CU.USERNAME = '"
                        + contractNo
                        + "'))"
                        + " ORDER BY CCR.TIMESTMP DESC";
        Log.info("Query: " + query);
        try {
            return CommonDao.selectFirstRowAsString(this.jdbcTemplate,query);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public String getOTP() throws Throwable {
        String query = "select code from CS_CODE_REQUESTS order by TIMESTMP desc";
        try {
            return CommonDao.selectFirstRowAsString(this.jdbcTemplate,query);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public String getCUIDbyUsername(String username) throws Throwable {
        String query = "select cuid from cs_user where username = '"
                        + username
                        + "'";
        try {
            return CommonDao.selectFirstRowAsString(this.jdbcTemplate,query);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public boolean isAccountExisted(String phone) {
        String query = "select 1 from cs_user where username = '"
                + phone
                + "'";
        try {
            Log.info("Query: " + query);
            List<String> rows = this.jdbcTemplate.queryForList(query,String.class);
            if(!rows.isEmpty() && rows.size()>0) {
                Log.info("Found (" + rows.size() + ") results");
                return true;
            }
            Log.info("Phone number: " + phone + " does not exist");
            return false;
        }
        catch (Throwable e){
            throw e;
        }
    }

}
