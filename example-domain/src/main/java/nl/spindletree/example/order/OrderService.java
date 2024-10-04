package nl.spindletree.example.order;

import lombok.RequiredArgsConstructor;
import nl.spindletree.kuis.domain.BusinessService;
import nl.spindletree.kuis.domain.Exposed;

import javax.inject.Named;


@Named
@RequiredArgsConstructor
@BusinessService
public class OrderService {
    private final OrderRepository orderRepository;

    @Exposed
    Order createOrder(final Order order) {
        return orderRepository.save(order);
    }


    @Exposed
    String somthingElse(final String aStringParam) {
        // do nothing
        return "bla bla";
    }

    @Exposed
    void delete(final int anIntParam) {
        // do nothing
    }

    @Exposed
    int otherPrimitive(final int anIntParam, String aStringParam, final Integer aIntegerParam) {
        // do nothing
        return 0;
    }


    Order closeOrder(long id) {
        return new Order(1, "bla", OrderStatus.CLOSED);
    }

}
