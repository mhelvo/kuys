package nl.spindletree.example.order.persist;

import nl.spindletree.example.order.Order;
import nl.spindletree.example.order.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface SpringOrderRepository extends JpaRepository<Order, Long>, OrderRepository {

}
