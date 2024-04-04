package nl.spindltree.order;

import lombok.Data;
import nl.spindltree.kuis.DTO;

@DTO
@Data
public class Order {
    private long id;
    private String description;
    private OrderStatus status;
}
