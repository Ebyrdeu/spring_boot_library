package dev.ebrydeu.spring_boot_library.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false, name = "username")
    private String userName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name ="Fullname")
    private String fullName;

    @Column(name = "image")
    private String profilePicture;

    @Column(name = "email", nullable = false, unique = false)
    private String email = "default@email.com";

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition= "VARCHAR(20) Default 'GUEST'")
    private Role role;

    public void setFullName(String fullName) {
        String[] nameParts = fullName.trim().split("//s");
        firstName = nameParts.length >= 1 ? nameParts[0].trim() : "";
        lastName = nameParts.length >= 2 ? nameParts[1].trim() : "";
    }
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
