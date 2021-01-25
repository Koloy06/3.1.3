package ru.hasa.springbootapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.hasa.springbootapp.dto.RoleDto;
import ru.hasa.springbootapp.dto.UserDto;
import ru.hasa.springbootapp.dto.validate.UserCreate;
import ru.hasa.springbootapp.dto.validate.UserUpdate;
import ru.hasa.springbootapp.service.ApiRestUserService;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiRestUserController {

    private final ApiRestUserService apiRestUserService;

    @Autowired
    public ApiRestUserController(ApiRestUserService apiRestUserService) {
        this.apiRestUserService = apiRestUserService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUser() {
        return new ResponseEntity<>(apiRestUserService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return new ResponseEntity<>(apiRestUserService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(apiRestUserService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/users/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> saveUser(@RequestBody @Validated(value = {UserCreate.class}) UserDto userDto) {
        return new ResponseEntity<>(apiRestUserService.saveUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody @Validated(value = {UserUpdate.class}) UserDto userDto) {
        return new ResponseEntity<>(apiRestUserService.updateUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/delete/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        apiRestUserService.deleteUser(id);
    }
}

