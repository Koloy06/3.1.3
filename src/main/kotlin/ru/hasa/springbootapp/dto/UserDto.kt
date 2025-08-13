package ru.hasa.springbootapp.dto

import ru.hasa.springbootapp.dto.validate.UserCreate
import ru.hasa.springbootapp.dto.validate.UserUpdate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UserDto(
    @field:NotNull(groups = [UserUpdate::class], message = "Not be empty")
    var id: Long? = null,

    @field:NotEmpty(groups = [UserCreate::class, UserUpdate::class], message = "Not be empty")
    var username: String? = null,

    @field:NotNull(groups = [UserCreate::class, UserUpdate::class], message = "Not be empty")
    var age: Byte? = null,

    @field:NotEmpty(groups = [UserCreate::class, UserUpdate::class], message = "Not be empty")
    var password: String? = null,

    @field:NotEmpty(groups = [UserCreate::class, UserUpdate::class], message = "Not be empty")
    var mail: String? = null,

    @field:NotEmpty(groups = [UserCreate::class, UserUpdate::class], message = "Not be empty")
    var roles: MutableSet<RoleDto> = mutableSetOf()
)
