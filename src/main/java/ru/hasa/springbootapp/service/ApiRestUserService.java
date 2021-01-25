package ru.hasa.springbootapp.service;

import ru.hasa.springbootapp.dto.RoleDto;
import ru.hasa.springbootapp.dto.UserDto;

import java.util.List;

public interface ApiRestUserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);

    List<RoleDto> getAllRoles();
}
