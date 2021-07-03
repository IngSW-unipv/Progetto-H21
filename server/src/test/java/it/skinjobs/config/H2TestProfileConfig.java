package it.skinjobs.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Jessica Vecchia
 */

@Configuration
@EnableJpaRepositories(basePackages = { "it.skinjobs"})
@EnableTransactionManagement
public class H2TestProfileConfig {

   @Bean
   @Profile("test")
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("org.h2.Driver");
      dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
      return dataSource;
   }

   Properties hibernateProps() {
      Properties properties = new Properties();
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
      properties.setProperty("hibernate.show_sql", "true");
      properties.setProperty("hibernate.hbm2ddl.auto", "create");
      return properties;
   }

   @Bean
   LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
      LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
      factoryBean.setDataSource(dataSource());
      factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
      factoryBean.setPackagesToScan("it.skinjobs");
      factoryBean.setJpaProperties(hibernateProps());

      return factoryBean;
   }

   @Bean
   JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
      return transactionManager;
   }

   @Bean 
   @Primary
   EntityManagerFactory entityManagerFactory() {
      return this.entityManagerFactoryBean().getObject();
   }

}


