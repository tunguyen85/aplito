package utilities;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Properties;

public class JdbcSources {
    private Properties properties;
    public JdbcTemplate bslJdbcTemplate;
    public JdbcTemplate csJdbcTemplate;

    public JdbcSources(Properties properties) {
        this.properties = properties;

        //instantiate the bslJdbcTemplate
        BasicDataSource dsBSL = new BasicDataSource();
        dsBSL.setDriverClassName(this.properties.getProperty("db.driver"));
        dsBSL.setUrl(this.properties.getProperty("db.bsl.url"));
        dsBSL.setUsername(this.properties.getProperty("db.bsl.username"));
        dsBSL.setPassword(this.properties.getProperty("db.bsl.password"));
        this.bslJdbcTemplate = new JdbcTemplate(dsBSL);
        //instantiate the csJdbcTemplate
        BasicDataSource dsMobile = new BasicDataSource();
        dsMobile.setDriverClassName(this.properties.getProperty("db.driver"));
        dsMobile.setUrl(this.properties.getProperty("db.cs.url"));
        dsMobile.setUsername(this.properties.getProperty("db.cs.username"));
        dsMobile.setPassword(this.properties.getProperty("db.cs.password"));
        this.csJdbcTemplate = new JdbcTemplate(dsMobile);

    }

    public JdbcTemplate getBslJdbcTemplate() {
        return bslJdbcTemplate;
    }

    public void setBslJdbcTemplate(JdbcTemplate bslJdbcTemplate) {
        this.bslJdbcTemplate = bslJdbcTemplate;
    }

    public JdbcTemplate getCsJdbcTemplate() {
        return csJdbcTemplate;
    }

    public void setCsJdbcTemplate(JdbcTemplate csJdbcTemplate) {
        this.csJdbcTemplate = csJdbcTemplate;
    }
}
