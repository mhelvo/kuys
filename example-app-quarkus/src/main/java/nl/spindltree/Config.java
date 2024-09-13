package nl.spindltree;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import nl.spindletree.example.order.OrderService;
import nl.spindltree.persist.QuarkusSqlORderRepository;

@Singleton
public class Config {
    @Inject
    QuarkusSqlORderRepository repository;

    @Produces
    @ApplicationScoped
    public OrderService orderService() {
        return new OrderService(repository);
    }
}
