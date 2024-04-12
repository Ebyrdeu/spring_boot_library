package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MessageRepository extends ListPagingAndSortingRepository<Message, Long>, ListCrudRepository<Message, Long> {
    List<Message> findByTitle(String title);
    List<Message> findMessageByIsPrivateFalse();

    List<Message> findAll();

    List<Message> findMessageByIsPrivateFalse();

    @Query(value = """
            select * from messages where is private = FALSE and id > ?1 limit ?2
            """, nativeQuery = true)
    List<Message> findMessagesByAndPrivate(long cursor, int pageSize);

    @Query(value = """
            select * from messages where id > ?1 limit ?2
            """, nativeQuery = true)
    Collection<Object> findMessagesBy(int p, int i);
}






