package nl.spindltree.example.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.spindltree.example.test.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static nl.spindltree.example.test.OrderTestBuilders.orderDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testCreate() throws Exception {
        OrderDtoTestBuilder builder = orderDto();
        builder.description("Another description needed here!");
        OrderDto order = builder.build();

        mockMvc.perform(post("/orders")
                        .content(toJson(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Another description needed here!"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    private String toJson(OrderDto order) throws JsonProcessingException {
        return objectMapper.writeValueAsString(order);
    }
}