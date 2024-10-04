package nl.spindltree.in;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.spindletree.example.order.OrderDto;
import nl.spindletree.example.order.OrderServiceAdapter;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {

    @Inject
    OrderServiceAdapter orderService;

    @POST
    @Transactional
    public Response createOrder(OrderDto orderDto) {
        OrderDto updatedOrder = orderService.createOrder(orderDto);

        return Response.ok(updatedOrder).build();
    }
}
