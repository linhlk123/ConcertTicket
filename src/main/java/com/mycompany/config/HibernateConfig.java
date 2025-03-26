// package com.mycompany.config;

// import java.util.Properties;

// import javax.sql.DataSource;

// import org.hibernate.SessionFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

// import jakarta.persistence.EntityManagerFactory;

// @Configuration
// public class HibernateConfig {

//     //@Bean
//     public SessionFactory sessionFactory() {
//         org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
//         return configuration.buildSessionFactory();
//     }

//     @Autowired
//     private DataSource dataSource;

//     @Bean
//     public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//         LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//         em.setDataSource(dataSource);
//         em.setPackagesToScan(new String[] { "com.mycompany.Model" });

//         HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//         em.setJpaVendorAdapter(vendorAdapter);
//         em.setJpaProperties(hibernateProperties());

//         return em;
//     }

//     @Bean
//     public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//         JpaTransactionManager transactionManager = new JpaTransactionManager();
//         transactionManager.setEntityManagerFactory(entityManagerFactory);
//         return transactionManager;
//     }

//     private Properties hibernateProperties() {
//         Properties properties = new Properties();
//         properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
//         properties.put("hibernate.show_sql", "true");
//         properties.put("hibernate.hbm2ddl.auto", "update");
//         return properties;
//     }
// }