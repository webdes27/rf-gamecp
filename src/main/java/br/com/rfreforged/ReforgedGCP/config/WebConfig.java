package br.com.rfreforged.ReforgedGCP.config;

import br.com.rfreforged.ReforgedGCP.model.servidor.DBConfig;
import br.com.rfreforged.ReforgedGCP.model.servidor.DBConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class WebConfig {

    private DBConfig config;

    public WebConfig() {
        File file = Paths.get(System.getProperty("user.dir") + "\\config\\database_connection.json").toFile();
        try {
            config = new ObjectMapper().readValue(file, DBConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean(name = "dbBilling")
    public DataSource dataSource1() {
        return getDataSource(config.getBilling());
    }

    @Bean(name = "jdbcTempBilling")
    public JdbcTemplate jdbcTempBilling(@Qualifier("dbBilling") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "dbRFUser")
    public DataSource dsRFUser() {
        return getDataSource(config.getRfUser());
    }

    @Bean(name = "jdbcTempRFUser")
    public JdbcTemplate jdbcTempRFUser(@Qualifier("dbRFUser") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "dbRFWorld")
    public DataSource dataSource2() {
        return getDataSource(config.getRfWorld());
    }

    @Bean(name = "jdbcTempRFWorld")
    public JdbcTemplate jdbcTempRFWorld(@Qualifier("dbRFWorld") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "dbGameCP")
    public DataSource dataSource3() {
        return getDataSource(config.getGamecp());
    }

    @Bean(name = "jdbcTempGameCP")
    public JdbcTemplate jdbcTempGameCP(@Qualifier("dbGameCP") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    private String getFullDBURL(String host, int port, String db) {
        return "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + db + "";
    }

    private DataSource getDataSource(DBConnection dbConnection) {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setRemoveAbandoned(true);
        poolProperties.setRemoveAbandonedTimeout(30);
        poolProperties.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        poolProperties.setUrl(getFullDBURL(dbConnection.getHost(), dbConnection.getPort(), dbConnection.getDb()));
        poolProperties.setUsername(dbConnection.getUsername());
        poolProperties.setPassword(dbConnection.getPassword());
        poolProperties.setMaxActive(10);
        poolProperties.setMaxIdle(10);
        return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
    }
}
