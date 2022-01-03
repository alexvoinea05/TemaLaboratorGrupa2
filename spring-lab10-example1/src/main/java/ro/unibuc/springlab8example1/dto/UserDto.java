package ro.unibuc.springlab8example1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotNull
    private String username;

    private String fullName;

    @Size(min = 12, max = 12)
    private String cnp;

    private Integer age;

    private String otherInformation;

    private ClassroomDto idClassroom;
}
