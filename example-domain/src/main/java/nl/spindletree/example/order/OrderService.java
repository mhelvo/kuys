package nl.spindletree.example.order;

import jakarta.inject.Named;

import static nl.spindletree.example.order.OrderDtoMapper.toOrder;
import static nl.spindletree.example.order.OrderDtoMapper.toOrderDto;


@Named
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto createOrder(final OrderDto orderDto) {
        final Order order = toOrder(orderDto);
        final Order updatedOrder = orderRepository.save(order);
        return toOrderDto(updatedOrder);
    }

    public OrderDto closeOrder(long id) {
        return new OrderDto(1, "bla", OrderStatus.CLOSED);
    }

}
