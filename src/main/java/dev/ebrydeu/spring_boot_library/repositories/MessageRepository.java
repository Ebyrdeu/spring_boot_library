package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends ListCrudRepository<Message, Long> {
    List<Message> findByAuthor(String author); // <>

    List<Message> findByTitle(String title);

    @Query("""
            update Message m set m.body = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    int editMessageBody(String body, Long id);

    @Query("""
            update Message m set m.title = ?1 where m.id = ?2
            """)
    @Modifying
    @Transactional
    int editMessageTitle(String title, Long id);
}
