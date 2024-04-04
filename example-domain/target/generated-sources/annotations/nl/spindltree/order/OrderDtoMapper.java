package nl.spindltree.order;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderDtoMapper {

    public static nl.spindltree.order.OrderDto toOrderDto(nl.spindltree.order.Order order) {
        return new nl.spindltree.order.OrderDto(
                    order.getId(), 
                    order.getDescription(), 
                    order.getStatus()
                );

}

    public static nl.spindltree.order.Order toOrder(nl.spindltree.order.OrderDto orderDto) {
        nl.spindltree.order.Order order = new nl.spindltree.order.Order();
            order.setId(orderDto.getId());
            order.setDescription(orderDto.getDescription());
            order.setStatus(orderDto.getStatus());
        return order;
    }

}