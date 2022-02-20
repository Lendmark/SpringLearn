package pl.lendemark.bookaro.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lendemark.bookaro.order.application.port.ManipulateOrderUseCase;
import pl.lendemark.bookaro.order.domain.Order;
import pl.lendemark.bookaro.order.domain.OrderRepository;
import pl.lendemark.bookaro.order.domain.OrderStatus;

@Service
@RequiredArgsConstructor
class ManipulateOrderService implements ManipulateOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        Order save = orderRepository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        orderRepository.findById(id)
                .ifPresent(order -> {
                    order.setStatus(status);
                    orderRepository.save(order);
                });
    }
}
