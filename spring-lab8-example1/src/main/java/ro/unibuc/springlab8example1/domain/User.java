package ro.unibuc.springlab8example1.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    private Long id;
    private String username;
    private String fullName;
    private UserDetails userDetails;
    private UserType userType;
    private LocalDateTime accountCreated;

}
