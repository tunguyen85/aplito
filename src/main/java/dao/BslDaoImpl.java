package dao;

import org.springframework.jdbc.core.JdbcTemplate;
import utilities.Environment;

import java.util.List;
import java.util.Map;

public class BslDaoImpl implements IBslDao {

    private JdbcTemplate jdbcTemplate;

    public BslDaoImpl(){
        this.jdbcTemplate = Environment.dataSources.getBslJdbcTemplate();
    }

    @Override
    public String queryContracByStatusAndType(String status, String contractType) throws Throwable {
        String query = "select CONTRACT_CODE\n" +
                "        from HO.BSL_CONTRACT sample(10) \n" +
                "        where STATUS = '" + status +"'\n" +
                "        and CONTRACT_TYPE = '" + contractType + "'\n" +
                "        and rownum <= 1" ;
        try {
            return CommonDao.selectSingleResultAsString(this.jdbcTemplate,query);
        }
        catch (Throwable e){
            throw e;
        }
    }

    @Override
    public String queryPrimaryPhoneByCUID(String cuid) throws Throwable {
        String query = "select p.phone_number \n" +
                "        from PIF.PIF_PARTY_ROLE c join PIF.PIF_CONTACT p on c.id = p.Party_role_id \n" +
                "        where c.EXTERNAL_ID = '" + cuid + "'\n" +
                "        and p.CLASSIFICATION = 'PRIMARY_MOBILE'\n" +
                "        and p.ACTIVE_YN = 1";
        try {
            return CommonDao.selectSingleResultAsString(this.jdbcTemplate,query);
        }
        catch (Throwable e){
            if (e.getMessage().startsWith("No result found for query: select ")){
                return "";
            }
            else throw e;
        }
    }

    @Override
    public String queryCuidFromContractNo(String contractNo) throws Throwable {
        String query = "select cl.cuid from ho.bsl_contract c\n" +
                "        join HO.BSL_CLIENT_SNAPSHOT s on c.CLIENT_SNAPSHOT_ID = s.ID\n" +
                "        join HO.BSL_CLIENT cl on s.CLIENT_ID = cl.ID\n" +
                "        where c.CONTRACT_CODE = '" + contractNo + "'";
        try {
            return CommonDao.selectSingleResultAsString(this.jdbcTemplate,query);
        }
        catch (Throwable e){
           throw e;
        }
    }

    /******TUNT2*******/
    @Override
    public List<Map<String, Object>> queryRandomPairOfPrimaryPhoneAndContract() throws Throwable {
        String query = "select c.STATUS, c.contract_code, p.phone_number, cl.cuid\n" +
                "       from ho.bsl_contract sample(10) c \n" +
                "       join HO.BSL_CLIENT_SNAPSHOT s on c.CLIENT_SNAPSHOT_ID = s.ID\n" +
                "       join HO.BSL_CLIENT cl on s.CLIENT_ID = cl.ID\n" +
                "       join PIF.PIF_PARTY_ROLE pr on cl.CUID = pr.EXTERNAL_ID\n" +
                "       join PIF.PIF_CONTACT p on pr.id = p.Party_role_id\n" +
                "       where c.STATUS in ('A','S','N','K')\n" +
                "       and c.CONTRACT_TYPE = 'CEL'\n" +
                "       and p.CLASSIFICATION = 'PRIMARY_MOBILE' \n" +
                "       and p.ACTIVE_YN = 1\n" +
                "       and rownum <= 1";
        try{
            return CommonDao.selectResultFromDataTable(this.jdbcTemplate, query);

        }catch (Throwable e){throw e;}
    }
}
