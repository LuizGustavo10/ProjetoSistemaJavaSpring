package com.dev.loja;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {
		
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("org.postgresql.Driver"); //declara as configurações de acesso
			dataSource.setUrl("jdbc:postgresql://localhost:5432/loja");
			dataSource.setUsername("postgres");//usuario
			dataSource.setPassword("postgres");//senha
			return dataSource;
		}
		
		@Bean
		public JpaVendorAdapter jpaVendorAdapter() {
			HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
			adapter.setDatabase(Database.POSTGRESQL); //driver do banco
			adapter.setShowSql(true); //mostrar no console o sql, é interessante
			adapter.setGenerateDdl(true);
			adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
			adapter.setPrepareConnection(true);
			return adapter;
		}

//		@Bean
//		public DataSource dataSource() {
//			DriverManagerDataSource dataSource = new DriverManagerDataSource();
//			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//			dataSource.setUrl("jdbc:mysql://localhost:3306/comapp?useTimezone=true&serverTimezone=America/Sao_Paulo");
//			dataSource.setUsername("root");
//			dataSource.setPassword("");
//			return dataSource;
//		}
	//	
//		@Bean
//		public JpaVendorAdapter jpaVendorAdapter() {
//			HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//			adapter.setDatabase(Database.MYSQL);
//			adapter.setShowSql(true);
//			adapter.setGenerateDdl(true);
//			adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
//			adapter.setPrepareConnection(true);
//			return adapter;
//		}
//	}

}
