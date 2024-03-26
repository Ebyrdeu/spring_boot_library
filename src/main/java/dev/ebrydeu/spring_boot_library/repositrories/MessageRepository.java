package dev.ebrydeu.spring_boot_library.repositrories;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends ListCrudRepository<Message, Long> {
    List<Message> findAll();

    List<Message> findByAuthor(String author); // <>

    List<Message> findByTitle(String title);

    @Query("""
            update Message m set m.body = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    void editMessageBody(String body, Long id);

    @Query("""
            update Message m set m.title = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    void editMessageTitle(String title, Long id);
}
