package pl.lendemark.bookaro.order.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lendemark.bookaro.order.domain.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
