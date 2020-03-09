package br.com.rfreforged.ReforgedGCP.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WebConfig {

    @Bean(name = "dbBilling")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource1() throws ClassNotFoundException {
        return DataSourceBuilder.create(Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getClassLoader()).build();
    }

    @Bean(name = "jdbcTempBilling")
    public JdbcTemplate jdbcTempBilling(@Qualifier("dbBilling") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "dbRFUser")
    @ConfigurationProperties(prefix = "spring.second-db")
    public DataSource dsRFUser() throws ClassNotFoundException {
        return DataSourceBuilder.create(Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getClassLoader()).build();
    }

    @Bean(name = "jdbcTempRFUser")
    public JdbcTemplate jdbcTempRFUser(@Qualifier("dbRFUser") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "dbRFWorld")
    @ConfigurationProperties(prefix = "spring.third-db")
    public DataSource dataSource2() throws ClassNotFoundException {
        return DataSourceBuilder.create(Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getClassLoader()).build();
    }

    @Bean(name = "jdbcTempRFWorld")
    public JdbcTemplate jdbcTempRFWorld(@Qualifier("dbRFWorld") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "dbGameCP")
    @ConfigurationProperties(prefix = "spring.fourth-db")
    public DataSource dataSource3() throws ClassNotFoundException {
        return DataSourceBuilder.create(Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getClassLoader()).build();
    }

    @Bean(name = "jdbcTempGameCP")
    public JdbcTemplate jdbcTempGameCP(@Qualifier("dbGameCP") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
