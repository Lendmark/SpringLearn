package pl.lendemark.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class BookaroStoreOnlineApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookaroStoreOnlineApplication.class, args);

    }

    private final CatalogService catalogService;


    public BookaroStoreOnlineApplication(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public void run(String... args) throws Exception {
        //CatalogService service = new CatalogService();
        List<Book> books = catalogService.findByTitle("Pan Tadeusz");
        books.forEach(System.out::println);
    }
}
