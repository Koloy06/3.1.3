package ru.hasa.springbootapp.dto;

import lombok.*;
import ru.hasa.springbootapp.dto.validate.UserCreate;
import ru.hasa.springbootapp.dto.validate.UserUpdate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    @NotNull(groups = {UserUpdate.class}, message = "Not be empty")
    private Long id;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class}, message = "Not be empty")
    private String username;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class}, message = "Not be empty")
    private Byte age;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class}, message = "Not be empty")
    private String password;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class}, message = "Not be empty")
    private String mail;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class}, message = "Not be empty")
    private Set<RoleDto> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(age, userDto.age) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(mail, userDto.mail) &&
                Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, age, password, mail, roles);
    }
}
