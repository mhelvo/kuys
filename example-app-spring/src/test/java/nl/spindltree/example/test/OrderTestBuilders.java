package nl.spindltree.example.test;

import nl.spindltree.example.order.OrderDto;
import nl.spindltree.example.order.OrderDtoTestBuilder;
import nl.spindltree.kuis.test.TestBuilder;

public class OrderTestBuilders {

    @TestBuilder(baseClass = OrderDto.class, contentFile = "test-builders/orderDto.json")
    public static OrderDtoTestBuilder orderDto() {
        return OrderDtoTestBuilder.initialize();
    }
}
