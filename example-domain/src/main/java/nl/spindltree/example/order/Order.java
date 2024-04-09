package nl.spindltree.example.order;

import lombok.Data;
import nl.spindltree.kuis.domain.BusinessEntity;

@BusinessEntity
@Data
public class Order {
    private long id;
    private String description;
    private OrderStatus status;
}
