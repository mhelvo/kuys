package nl.spindltree.order;

import lombok.Data;
import nl.spindltree.kuis.Dto;

@Dto
@Data
public class Order {
    private long id;
    private String description;
    private OrderStatus status;
}
