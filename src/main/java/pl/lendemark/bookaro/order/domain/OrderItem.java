package pl.lendemark.bookaro.order.domain;

import lombok.Value;
import pl.lendemark.bookaro.catalog.domain.Book;

@Value
public
class OrderItem {
    Book book;
    int quantity;
}
