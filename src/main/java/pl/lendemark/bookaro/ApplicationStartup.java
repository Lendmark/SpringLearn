package pl.lendemark.bookaro;

import lombok.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lendemark.bookaro.catalog.application.port.CatalogUseCase;
import pl.lendemark.bookaro.catalog.domain.Book;
import pl.lendemark.bookaro.order.application.port.PlaceOrderUseCase;
import pl.lendemark.bookaro.order.application.port.QueryOrderUseCase;
import pl.lendemark.bookaro.order.domain.OrderItem;
import pl.lendemark.bookaro.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

@Component
class ApplicationStartup implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            PlaceOrderUseCase placeOrder,
            QueryOrderUseCase queryOrder,
            @Value("${bookaro.catalog.query}") String title,
            @Value("${bookaro.catalog.limit}") Long limit
    ) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book panTadeusz = catalog.findOneByTitle("Pan Tadeusz")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book chlopi = catalog.findOneByTitle("Chłopi")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        // create recipient
        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
                .phone("123-456-789")
                .street("Armii Krajowej 31")
                .city("Krakow")
                .zipCode("30-150")
                .email("jan@example.org")
                .build();

        PlaceOrderUseCase.PlaceOrderCommand command = PlaceOrderUseCase.PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz, 16))
                .item(new OrderItem(chlopi, 7))
                .build();

        PlaceOrderUseCase.PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());

        // list all orders
        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order);
                });
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void initData() {
        catalog.addBook(new CatalogUseCase.CreateCommandBook("Pan Tadeusz", "Adam Mickiewicz", 1834, new BigDecimal("19.90")));
        catalog.addBook(new CatalogUseCase.CreateCommandBook("Ogniem i Mieczem", "Henryk Sienkiewicz", 1884, new BigDecimal("29.90")));
        catalog.addBook(new CatalogUseCase.CreateCommandBook("Chłopi", "Władysław Reymont", 1904, new BigDecimal("11.90")));
        catalog.addBook(new CatalogUseCase.CreateCommandBook("Pan Wołodyjowski", "Henryk Sienkiewicz", 1899, new BigDecimal("14.90")));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    CatalogUseCase.UpdataBookCommand command = CatalogUseCase.UpdataBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli Ostatni Zajazd na Litwie")
                            .build();
                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }
}
