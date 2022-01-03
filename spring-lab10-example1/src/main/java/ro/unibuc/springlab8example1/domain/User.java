package ro.unibuc.springlab8example1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "users_user_details",
            joinColumns = {
                    @JoinColumn(name = "users", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_details", referencedColumnName = "id")
            }
    )
    private UserDetails userDetails;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "account_created")
    private LocalDateTime accountCreated;

    @ManyToOne
    @JoinColumn(name="id_classroom", referencedColumnName="id_classroom")
    private Classroom idClassroom;
}
