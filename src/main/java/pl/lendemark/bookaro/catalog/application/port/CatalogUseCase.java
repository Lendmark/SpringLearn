package pl.lendemark.bookaro.catalog.application.port;

import lombok.Value;
import pl.lendemark.bookaro.catalog.domain.Book;

import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    void addBook(CreateCommandBook createCommandBook);

    void removeById(Long id);

    void updateBook();

    @Value
    class CreateCommandBook{
        String title;
        String author;
        Integer year;
    }
}
