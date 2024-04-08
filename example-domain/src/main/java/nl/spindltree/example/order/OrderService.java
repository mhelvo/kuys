package nl.spindltree.example.order;

import javax.inject.Named;

import static nl.spindltree.example.order.OrderDtoMapper.toOrder;
import static nl.spindltree.example.order.OrderDtoMapper.toOrderDto;


@Named
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
