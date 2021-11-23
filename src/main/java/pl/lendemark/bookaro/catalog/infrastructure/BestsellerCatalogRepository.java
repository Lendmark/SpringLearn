package pl.lendemark.bookaro.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.lendemark.bookaro.catalog.domain.Book;
import pl.lendemark.bookaro.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class BestsellerCatalogRepository implements CatalogRepository {
    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public BestsellerCatalogRepository() {
        storage.put(1L, new Book(1L, "abc", "Adam sada", 1834));
        storage.put(2L, new Book(2L, "asda i Mieczem", "dasda Sienkiewicz", 1834));
        storage.put(3L, new Book(1L, "sad", "asdtg Reymont", 1834));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
