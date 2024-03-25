package dev.ebrydeu.spring_boot_library.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@Entity
@Table (name = "MESSAGE")

@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull private UUID id;

    @NonNull private String title;
    @NonNull private String body;
    @NonNull private String author;
    @NonNull private String date; //can it be Date obj?

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="User")
    private User user;


}
