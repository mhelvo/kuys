package nl.spindletree.example.order;

import lombok.Data;
import nl.spindletree.kuis.domain.BusinessEntity;

@BusinessEntity
@Data
public class Order {
    private long id;
    private String description;
    private OrderStatus status;
}
