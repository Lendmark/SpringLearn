package pl.lendemark.bookaro.order.application;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import pl.lendemark.bookaro.order.application.port.QueryOrderUseCase;
import pl.lendemark.bookaro.order.domain.Order;
import pl.lendemark.bookaro.order.domain.OrderRepository;

import java.util.List;

@Service
@AllArgsConstructor
class QueryOrderService implements QueryOrderUseCase {
    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }
}
