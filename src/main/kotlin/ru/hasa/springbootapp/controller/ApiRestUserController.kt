package ru.hasa.springbootapp.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.hasa.springbootapp.dto.RoleDto
import ru.hasa.springbootapp.dto.UserDto
import ru.hasa.springbootapp.dto.validate.UserCreate
import ru.hasa.springbootapp.dto.validate.UserUpdate
import ru.hasa.springbootapp.service.ApiRestUserService

@RestController
@RequestMapping("/api/v1")
class ApiRestUserController(private val apiRestUserService: ApiRestUserService) {

    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers(): ResponseEntity<List<UserDto>> =
        ResponseEntity(apiRestUserService.getAllUsers(), HttpStatus.OK)

    @GetMapping("/roles", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllRoles(): ResponseEntity<List<RoleDto>> =
        ResponseEntity(apiRestUserService.getAllRoles(), HttpStatus.OK)

    @GetMapping("/users/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDto> =
        ResponseEntity(apiRestUserService.getUserById(id), HttpStatus.OK)

    @PostMapping("/users/create", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveUser(@RequestBody @Validated(UserCreate::class) userDto: UserDto): ResponseEntity<UserDto> =
        ResponseEntity(apiRestUserService.saveUser(userDto), HttpStatus.CREATED)

    @PutMapping("/users/update", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(@RequestBody @Validated(UserUpdate::class) userDto: UserDto): ResponseEntity<UserDto> =
        ResponseEntity(apiRestUserService.updateUser(userDto), HttpStatus.OK)

    @DeleteMapping("/users/delete/{id}")
    fun deleteUser(@PathVariable id: Long) {
        apiRestUserService.deleteUser(id)
    }
}
