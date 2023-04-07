package de.joshuaschnabel.wim.application;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ R2dbcProperties.class, FlywayProperties.class })
class DatabaseConfig {

	@Value("${spring.profiles.active:Unknown}")
	private String activeProfiles;

	@Bean(initMethod = "migrate")
	public Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
		var h2 = this.activeProfiles.contains("h2");
		var mysql = this.activeProfiles.contains("mysql");

		var suffix = h2 ? "h2" : mysql ? "mysql" : "";

		return Flyway.configure()
				.dataSource(flywayProperties.getUrl(), r2dbcProperties.getUsername(), r2dbcProperties.getPassword())
				.locations(flywayProperties.getLocations().stream().map(x -> x + suffix).toArray(String[]::new))
				.baselineOnMigrate(true).load();
	}
}