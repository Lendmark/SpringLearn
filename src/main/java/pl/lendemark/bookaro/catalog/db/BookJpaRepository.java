package pl.lendemark.bookaro.catalog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lendemark.bookaro.catalog.domain.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

}
