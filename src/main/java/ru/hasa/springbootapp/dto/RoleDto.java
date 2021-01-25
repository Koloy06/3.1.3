package ru.hasa.springbootapp.dto;

import lombok.*;
import ru.hasa.springbootapp.dto.validate.UserCreate;
import ru.hasa.springbootapp.dto.validate.UserUpdate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleDto {

    @NotNull(groups = {UserUpdate.class}, message = "Not be empty")
    private Long id;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class}, message = "Not be empty")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id) &&
                Objects.equals(name, roleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
