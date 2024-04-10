package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends ListPagingAndSortingRepository<Message, Long>, ListCrudRepository<Message, Long> {
    List<Message> findByTitle(String title);
    List<Message> findMessageByIsPrivateFalse();

    List<Message> findAll();

    @Query(value = """
            select * from messages where id > ?1 limit ?2
            """, nativeQuery = true)
    List<Message> findById(long cursor, int pageSize);

    @Query(value = """
            select * from messages where is private = FALSE and id > ?1 limit ?2
            """, nativeQuery = true)
    List<Message> findPublicMessages(long cursor, int pageSize);
}






