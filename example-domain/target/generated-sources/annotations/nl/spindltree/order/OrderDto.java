package nl.spindltree.order;

import lombok.Value;
@Value
public class OrderDto {

long id;
java.lang.String description;
nl.spindltree.order.OrderStatus status;
}
