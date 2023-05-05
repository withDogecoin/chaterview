package team.backend.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.hikari")
    fun dataSourceHikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Primary
    @Bean(name = ["primaryDataSource"])
    fun primaryDataSource(@Qualifier("dataSourceHikariConfig") config: HikariConfig): DataSource {
        val hikariDataSource = HikariDataSource(config)
        return LazyConnectionDataSourceProxy(hikariDataSource)
    }
}