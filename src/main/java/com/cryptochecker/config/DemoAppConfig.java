package com.cryptochecker.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.cryptochecker")
@PropertySource("classpath:persistence.properties")
public class DemoAppConfig implements WebMvcConfigurer  {

	@Autowired
	Environment env;
	
//	ComboPooledDataSource securityDataSource = new ComboPooledDataSource(); 
//	
//	//set the jdbc driver class
//	try {
//		securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
//	} catch (PropertyVetoException e) {	
//		throw new RuntimeException(e);
//	}	
	
	@Bean
	public DataSource getDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		try {
			dataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {	
			throw new RuntimeException(e);
		}
		
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		
		dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("connection.pool.initialPoolSize")));
		dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));
		dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));
		dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));
		
		return dataSource;
	}

	private Properties getHibernateProperties() {
        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        
        return props;
    }
	
	@Bean
    public LocalSessionFactoryBean sessionFactory(){
        // create session factorys
		System.out.println("****test");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        
        // set the properties
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());
        
        return sessionFactory;
    }
	
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        
        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    } 
	
	// define a bean for ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/");
    } 
	
}









