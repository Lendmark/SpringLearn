package pl.lendemark.bookaro.catalog.application.port;

import lombok.Builder;
import lombok.Value;
import pl.lendemark.bookaro.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public interface CatalogUseCase {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByTitleAndAuthor(String title, String author);

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    Optional<Book> findOneByTitle(String title);

    void addBook(CreateCommandBook createCommandBook);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdataBookCommand command);

    @Value
    class CreateCommandBook{
        String title;
        String author;
        Integer year;
        BigDecimal price;

        public Book toBook() {
            return new Book(title, author, year, price);
        }
    }

    @Value
    @Builder
    class UpdataBookCommand {
        Long id;
        String title;
        String author;
        Integer year;

        public Book updateFields(Book book) {
            if (title != null) {
                book.setTitle(title);
            }
            if (author != null){
                book.setAuthor(author);
            }
            if (year != null) {
                book.setYear(year);
            }
            return book;
        }
    }

    @Value
    class UpdateBookResponse{
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, emptyList());

        boolean success;
        List<String> errors;
    }
}
