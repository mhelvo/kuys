package nl.spindletree.example.order.persist;

import nl.spindletree.example.order.Order;
import nl.spindletree.example.order.OrderRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpringOrderMongoDBRepository extends MongoRepository<Order, Long>, OrderRepository {

}
