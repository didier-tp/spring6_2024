dans pom.xml
==============
	<dependency>
			<groupId>org.liquibase</groupId>
			<artifactId>liquibase-core</artifactId>
	</dependency>


dans application.properties
===========================
spring.jpa.hibernate.ddl-auto=none
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml

dans src/main/resources:
========================
  db
    changelog
       db.changelog-master.yaml
       changelog1.yaml