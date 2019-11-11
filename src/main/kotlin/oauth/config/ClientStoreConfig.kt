package oauth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

import javax.sql.DataSource
import java.util.HashMap

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = ["oauth.client"],
        entityManagerFactoryRef = "clientEntityManager",
        transactionManagerRef = "clientTransactionManager")
class ClientStoreConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.auth.datasource")
    fun clientDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun clientEntityManager(): LocalContainerEntityManagerFactoryBean {

        val properties = HashMap<String, Any>()
        properties["hibernate.hbm2ddl.auto"] = "update"
        properties["hibernate.dialect"] = "org.hibernate.dialect.MySQL5InnoDBDialect"

        return LocalContainerEntityManagerFactoryBean().apply {
            dataSource = clientDataSource()
            setPackagesToScan("oauth.client")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setJpaPropertyMap(properties)
        }
    }

    @Bean
    fun clientTransactionManager(): PlatformTransactionManager =
            JpaTransactionManager().apply {
                entityManagerFactory = clientEntityManager().getObject()
            }
}
