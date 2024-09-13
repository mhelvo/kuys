package nl.spindletree.example.order;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import nl.spindletree.kuis.domain.BusinessEntity;

@BusinessEntity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private OrderStatus status;
}
