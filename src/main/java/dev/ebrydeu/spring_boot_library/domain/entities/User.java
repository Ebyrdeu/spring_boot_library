package dev.ebrydeu.spring_boot_library.domain.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;
@Data
@Entity
@Table(name = "USER")

@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull private UUID id;

    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String profileName;
    @NonNull private String profilePicture;
    @NonNull private String email;
}
