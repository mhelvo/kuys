package nl.spindltree;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.spindletree.example.order.Order;
import nl.spindletree.example.order.OrderDto;
import nl.spindletree.example.order.OrderDtoTestBuilder;
import nl.spindltree.persist.QuarkusSqlORderRepository;
import org.junit.jupiter.api.Test;

import static groovy.json.JsonOutput.toJson;
import static io.restassured.RestAssured.given;
import static nl.spindletree.example.order.OrderStatus.OPEN;
import static nl.spindltree.test.OrderTestBuilders.orderDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class ExampleResourceTest {

    @Inject
    QuarkusSqlORderRepository orderRepository;


    @Test
    @Transactional
    public void testCreate() throws Exception {
        OrderDtoTestBuilder builder = orderDto();
        builder.description("Another description needed here!");
        OrderDto order = builder.build();


        OrderDto returnedOrder = given()
                .contentType("application/json")
                .body(toJson(order))
                .when().post("/orders")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("description", equalTo("Another description needed here!"))
                .body("status", equalTo("OPEN"))
                .extract().as(OrderDto.class);

        Order storedOrder = orderRepository.findById(returnedOrder.getId());
        var all = orderRepository.findAll().stream().toList();

        assertThat(storedOrder.getDescription(), equalTo("Another description needed here!"));
        assertThat(storedOrder.getStatus(), equalTo(OPEN));
    }
}
