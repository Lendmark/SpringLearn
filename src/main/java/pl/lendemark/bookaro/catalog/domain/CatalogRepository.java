package pl.lendemark.bookaro.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    List<Book> findAll();

    Book save (Object book);

    Optional<Book> findById(Long id);

    void removeById(Long id);

}
