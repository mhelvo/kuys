package nl.spindltree.test;

import nl.spindletree.example.order.OrderDto;
import nl.spindletree.example.order.OrderDtoTestBuilder;
import nl.spindletree.kuis.test.TestBuilder;

public class OrderTestBuilders {

    @TestBuilder(baseClass = OrderDto.class, contentFile = "test-builders/orderDto.json")
    public static OrderDtoTestBuilder orderDto() {
        return OrderDtoTestBuilder.initialize();
    }
}
