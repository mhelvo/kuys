package nl.spindletree.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.tool.schema.spi.SchemaManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class OrderEntityConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Properties hibernateProperties;

    //    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example");

        Metadata metadata = buildMetadata();
        emf.setJpaProperties(hibernateProperties);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    private Metadata buildMetadata() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClassName("nl.spindletree.example.order.Order"); // Geef hier de volledige klasse-naam van Order op

        Metadata metadata = sources.buildMetadata();

        configureOrderEntity(metadata);

        return metadata;
    }

    private void configureOrderEntity(Metadata metadata) {
        PersistentClass orderClass = metadata.getEntityBinding("com.example.Order");

        // Configure ID as primary key with GenerationType.IDENTITY
        Property idProperty = orderClass.getProperty("id");
        idProperty.setNaturalIdentifier(true); // Markeer als natuurlijke sleutel

//        Column idColumn = (Column) idProperty.columnIterator().next();
//        idColumn.nullable(false); // Zorg ervoor dat ID niet-null is
//        idColumn.unique(true); // Maak ID kolom uniek

        // Configure generation strategy for ID column
        Dialect dialect = metadata.getDatabase().getDialect();
        if (dialect instanceof H2Dialect) {
//            idColumn.setValueGenerationStrategy(GenerationType.IDENTITY);
        } else {
            throw new SchemaManagementException("Unsupported dialect for identity column generation: " + dialect);
        }
    }
}
