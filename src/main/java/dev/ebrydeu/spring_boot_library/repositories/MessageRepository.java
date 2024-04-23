package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageAndUsername;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface MessageRepository extends ListCrudRepository<Message, Long>, ListPagingAndSortingRepository<Message, Long> {
    List<MessageAndUsername> findAllByPrivateMessageIsFalse();
    List<MessageAndUsername> findAllByPrivateMessageIsFalse(Pageable pageable);

    List<MessageAndUsername> findMessagesByUserId(Long userId);

    List<MessageAndUsername> findMessageByUserUserName(String username);

    List<MessageAndUsername> findAllByUser(User user);


    @EntityGraph(attributePaths = "user.userName")
    List<MessageAndUsername> findAllByUserIdAndPrivateMessageIsFalse(Long id);

}
