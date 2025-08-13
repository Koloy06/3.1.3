package ru.hasa.springbootapp.dto

import ru.hasa.springbootapp.dto.validate.UserCreate
import ru.hasa.springbootapp.dto.validate.UserUpdate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class RoleDto(
    @field:NotNull(groups = [UserUpdate::class], message = "Not be empty")
    var id: Long? = null,

    @field:NotEmpty(groups = [UserCreate::class, UserUpdate::class], message = "Not be empty")
    var name: String? = null
)
