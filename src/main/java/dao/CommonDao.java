package dao;

import cucumber.api.DataTable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import utilities.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommonDao {
    public static String selectSingleResultAsString(JdbcTemplate jdbcTemplate, String query) throws Throwable{
        try {
            Log.info("Query: " + query);
            String result = jdbcTemplate.queryForObject(query, String.class);
            Log.info("Result: " + result);
            return result;
        }
        catch (EmptyResultDataAccessException e){
            throw new Throwable("No result found for query: " + query);
        }
        catch (Throwable t){
            throw t;
        }
    }

    public static String selectFirstRowAsString(JdbcTemplate jdbcTemplate, String query) throws Throwable {
        try {
            Log.info("Query: " + query);
            List<String> rows = jdbcTemplate.queryForList(query, String.class);
            Log.info("Found (" + rows.size() + ") results.");
            if (rows.size() > 0) {
                String result = rows.get(0);
                Log.info("First result: " + result);
                return result;
            } else {
                throw new Throwable("No result found for query: " + query);
            }
        }
        catch (Throwable e){
            throw e;
        }
    }

    public static String selectRandomRowAsString(JdbcTemplate jdbcTemplate, String query) throws Throwable {
        try{
            Log.info("Query: " + query);
            List<String> rows = jdbcTemplate.queryForList(query,String.class);
            Log.info("Found (" + rows.size() + ") results.");
            if (rows.size() > 0) {
                Random r = new Random();
                int index = r.nextInt(rows.size() - 1);
                String result = rows.get(index);
                Log.info("Random result picked: " + result);
                return result;
            }
            else{
                throw new Throwable("No result found for query: " + query);
            }
        }
        catch (Throwable e){
            throw e;
        }
    }

    /******TUNT2*******/
    public static List<Map<String, Object>> selectResultFromDataTable(JdbcTemplate jdbcTemplate, String query) throws Throwable{
        try{
            Log.info("Query: " + query);
            List<Map<String, Object>> ls = jdbcTemplate.queryForList(query);
            Log.info("Result: " + ls.toString());
           return ls;
        }catch (EmptyResultDataAccessException e){
            throw new Throwable("No result found for query: " + query);
        }
    }
}
