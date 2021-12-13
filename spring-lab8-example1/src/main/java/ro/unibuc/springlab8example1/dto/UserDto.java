package ro.unibuc.springlab8example1.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;

    private String username;

    private String fullName;

    private String cnp;

    private Integer age;

    private String otherInformation;
}
