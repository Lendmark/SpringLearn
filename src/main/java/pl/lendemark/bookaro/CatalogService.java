package pl.lendemark.bookaro;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
class CatalogService {
    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public CatalogService() {
        storage.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1834));
        storage.put(2L, new Book(2L, "Ogniem i Mieczem", "Henryk Sienkiewicz", 1834));
        storage.put(3L, new Book(1L, "Chłopi", "Włądysław Reymont", 1834));
    }


    List<Book> findByTitle(String title){
        return storage.values()
                .stream()
                .filter(book -> book.title.startsWith(title))
                .collect(Collectors.toList());
    }

}
