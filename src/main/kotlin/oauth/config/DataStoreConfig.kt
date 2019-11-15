package oauth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
@EnableJpaRepositories(basePackages = ["venus.utillibrary.repository.base"], entityManagerFactoryRef = "entityManager")
class DataStoreConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    @Primary
    fun entityManager(): LocalContainerEntityManagerFactoryBean {

        val properties = HashMap<String, Any>()
        properties["hibernate.hbm2ddl.auto"] = "validate"
        properties["hibernate.dialect"] = "org.hibernate.dialect.MySQL5InnoDBDialect"

        return LocalContainerEntityManagerFactoryBean().apply {
            dataSource = dataSource()
            setPackagesToScan("venus.utillibrary.model.base")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setJpaPropertyMap(properties)
        }
    }

    @Bean
    @Primary
    fun transactionManager(): PlatformTransactionManager =
            JpaTransactionManager().apply {
                entityManagerFactory = entityManager().getObject()
            }
}
