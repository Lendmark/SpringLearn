package pl.lendemark.bookaro;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lendemark.bookaro.catalog.application.CatalogController;
import pl.lendemark.bookaro.catalog.domain.Book;

import java.util.List;

@RequiredArgsConstructor
@Component
class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;

    @Override
    public void run(String... args) throws Exception {
        //CatalogService service = new CatalogService();
        List<Book> books = catalogController.findByTitle("Pan Tadeusz");
        books.forEach(System.out::println);

        List<Book> books1 = catalogController.findByAuthor("Henryk");
        books1.forEach(System.out::println);
    }
}
