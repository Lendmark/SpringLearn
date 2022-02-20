package pl.lendemark.bookaro.order.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lendemark.bookaro.order.application.port.ManipulateOrderUseCase;
import pl.lendemark.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import pl.lendemark.bookaro.order.application.port.QueryOrderUseCase;
import pl.lendemark.bookaro.order.application.port.QueryOrderUseCase.RichOrder;
import pl.lendemark.bookaro.order.domain.Order;
import pl.lendemark.bookaro.order.domain.OrderItem;
import pl.lendemark.bookaro.order.domain.OrderStatus;
import pl.lendemark.bookaro.order.domain.Recipient;
import pl.lendemark.bookaro.web.CreatedURI;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/orders")
@RestController
@AllArgsConstructor
class OrderController {
    private final ManipulateOrderUseCase manipulateOrder;
    private final QueryOrderUseCase queryOrder;

    @GetMapping
    public List<RichOrder> getOrders(){
        return queryOrder.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RichOrder> findById(@PathVariable Long id){
        return queryOrder.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Object> createOrcer(@RequestBody CreateOrderCommand command){
        return manipulateOrder
                .placeOrder(command.toPlaceOrderCommand())
                .handle(
                        orderId -> ResponseEntity.created(orderUri(orderId)).build(),
                        error -> ResponseEntity.badRequest().body(error)
                );
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(ACCEPTED)
    public void updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusCommand command){
        OrderStatus orderStatus = OrderStatus
                .parseString(command.status)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Uknown status: " + command.status));
        manipulateOrder.updateOrderStatus(id, orderStatus);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteOrder(@PathVariable Long id){
        manipulateOrder.deleteOrderById(id);
    }

    URI orderUri(Long orderId){
        return new CreatedURI("/ " + orderId).toUri();
    }

    @Data
    static class CreateOrderCommand{
        List<OrderItemCommand> items;
        RecipientCommand recipient;

        PlaceOrderCommand toPlaceOrderCommand(){
            List<OrderItem> orderItems = items
                    .stream()
                    .map(item -> new OrderItem(item.bookId, item.quantity))
                    .collect(Collectors.toList());
            return new PlaceOrderCommand(orderItems, recipient.toRecipient());
        }
    }

    @Data
    static class OrderItemCommand{
        Long bookId;
        int quantity;
    }

    @Data
    static class RecipientCommand{
        String name;
        String phone;
        String street;
        String city;
        String zipCode;
        String email;

        Recipient toRecipient(){
            return new Recipient(name, phone, street, city, zipCode, email);
        }
    }

    @Data
    static class UpdateStatusCommand{
        String status;
    }

}
