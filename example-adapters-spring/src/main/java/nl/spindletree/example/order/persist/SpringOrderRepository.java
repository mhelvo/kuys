package nl.spindletree.example.order.persist;

import jakarta.persistence.Entity;
import nl.spindletree.example.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
@Entity
public interface SpringOrderRepository extends JpaRepository<Order, Long> {

}
