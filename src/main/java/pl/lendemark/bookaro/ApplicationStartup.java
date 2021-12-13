package pl.lendemark.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lendemark.bookaro.catalog.application.port.CatalogUseCase;
import pl.lendemark.bookaro.catalog.application.port.CatalogUseCase.CreateCommandBook;
import pl.lendemark.bookaro.catalog.domain.Book;

import java.util.List;

@Component
class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final Long limit;

    public ApplicationStartup(CatalogUseCase catalog, String title, Long limit){
        this.catalog = catalog;
        this.title = title;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        findByTitle();
    }

    private void initData(){
        catalog.addBook(new CreateCommandBook("abc", "Adam sada", 123));
        catalog.addBook(new CreateCommandBook("asda i Mieczem", "dasda Sienkiewicz", 1834));
        catalog.addBook(new CreateCommandBook("sad", "asdtg Reymont", 18213));

    }

    private void findByTitle(){
        List<Book> books = catalog.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);
    }
}
