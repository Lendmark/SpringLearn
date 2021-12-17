package pl.lendemark.bookaro.order.application.port;
import pl.lendemark.bookaro.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
