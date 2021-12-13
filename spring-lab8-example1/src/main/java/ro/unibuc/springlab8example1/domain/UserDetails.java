package ro.unibuc.springlab8example1.domain;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

    private Long id;
    private String cnp;
    private Integer age;
    private String otherInformation;

}
