package com.zyf.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@ComponentScans({
        @ComponentScan("com.zyf.service")
})
@MapperScan("com.zyf.mapper")
@EnableTransactionManagement
public class RootConfiguration {
    @Bean
    public DataSource dataSource(){
        HikariDataSource source = new HikariDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setJdbcUrl("jdbc:mysql://localhost:3306/book_manage?serverTimezone=UTC&characterEncoding=UTF-8");
        source.setUsername("root");
        source.setPassword("manager");
        return source;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

    @Bean
    public TransactionManager transactionManager(@Autowired DataSource dataSource){//开启事务
        return new DataSourceTransactionManager(dataSource);
    }

}
