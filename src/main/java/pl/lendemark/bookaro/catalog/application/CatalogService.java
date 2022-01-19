package pl.lendemark.bookaro.catalog.application;

import org.springframework.stereotype.Service;
import pl.lendemark.bookaro.catalog.application.port.CatalogUseCase;
import pl.lendemark.bookaro.catalog.domain.Book;
import pl.lendemark.bookaro.catalog.domain.CatalogRepository;

import java.util.Arrays;
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
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author){
        repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .findFirst();
        return Optional.empty();
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .findFirst();
    }

    @Override
    public Book addBook(CreateCommandBook command){
        Book book = command.toBook();
        repository.save(book);
        return book;

    }

    @Override
    public void removeById(Long id){
        repository.removeById(id);

    }

    @Override
    public UpdateBookResponse updateBook(UpdataBookCommand command) {
        repository.findById(command.getId())
                .map(book -> {
                    book.setTitle(command.getTitle());
                    book.setAuthor(command.getAuthor());
                    book.setYear(command.getYear());
                    repository.save(book);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(()-> new UpdateBookResponse(false, Arrays.asList("Book not found with id: " + command.getId())));
        return null;
    }


}
