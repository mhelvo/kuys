package nl.spindltree.order;

public class OrderService {


    public OrderDto createOrder(OrderDto orderDto) {
        //to Order

        //Return OrderDto

        return null;
    }

    public OrderDto closeOrder(long id) {
        return new OrderDto(1, "bla", OrderStatus.CLOSED);
    }

}
