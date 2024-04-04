package nl.spindltree.order;

import static nl.spindltree.order.OrderDtoMapper.toOrder;
import static nl.spindltree.order.OrderDtoMapper.toOrderDto;

public class OrderService {


    public OrderDto createOrder(OrderDto orderDto) {
        //to Order
        Order order = toOrder(orderDto);

        //save


        return toOrderDto(order);
    }

    public OrderDto closeOrder(long id) {
        return new OrderDto(1, "bla", OrderStatus.CLOSED);
    }

}
