package pl.lendemark.bookaro.catalog.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lendemark.bookaro.catalog.application.port.CatalogUseCase;
import pl.lendemark.bookaro.catalog.domain.Book;
import pl.lendemark.bookaro.catalog.domain.CatalogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;

    public CatalogService(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> findByTitle(String title){
        return repository.findAll().stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll(){
        return null;
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author){
        return Optional.empty();
    }

    @Override
    public void addBook(CreateCommandBook createCommandBook){
        Book book = new Book(createCommandBook.getTitle(), createCommandBook.getAuthor(), createCommandBook.getYear());
        repository.save(book);

    }

    @Override
    public void removeById(Long id){

    }

    @Override
    public void updateBook() {

    }
}
