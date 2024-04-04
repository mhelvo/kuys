package ${packageName};

import lombok.experimental.UtilityClass;

@UtilityClass
public class ${simpleClassName} {

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(
            order.getId(),
            order.getDescription(),
            order.getStatus());
    }

    public static Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setDescription(order.getDescription());
        order.setStatus(orderDto.getStatus());
        return order;
    }

}