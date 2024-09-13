package nl.spindltree.persist;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.spindletree.example.order.Order;
import nl.spindletree.example.order.OrderRepository;

@ApplicationScoped
public class QuarkusSqlORderRepository implements OrderRepository, PanacheRepository<Order> {
    @Override
    public Order save(Order order) {
        return getEntityManager().merge(order);
    }
}
