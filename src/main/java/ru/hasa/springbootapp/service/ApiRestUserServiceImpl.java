package ru.hasa.springbootapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hasa.springbootapp.dao.RoleDao;
import ru.hasa.springbootapp.dao.UserDao;
import ru.hasa.springbootapp.dto.RoleDto;
import ru.hasa.springbootapp.dto.UserDto;
import ru.hasa.springbootapp.model.Role;
import ru.hasa.springbootapp.model.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service()
public class ApiRestUserServiceImpl implements ApiRestUserService{


    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public ApiRestUserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        return mapListUserToListUserDto(allUsers);
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapUserToUserDto(userDao.showUser(id));
    }

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = mapUserDtoToUser(userDto);
        Set<Role> roles = user.getRoles();
        Set<Role> persistRoles = roles.stream().map(role -> roleDao.getRoleById(role.getId())).collect(Collectors.toSet());
        user.setRoles(persistRoles);
        return mapUserToUserDto(userDao.saveUser(user));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User user = mapUserDtoToUser(userDto);
        Set<Role> roles = user.getRoles();
        Set<Role> persistRoles = roles.stream().map(role -> roleDao.getRoleById(role.getId())).collect(Collectors.toSet());
        user.setRoles(persistRoles);
        return mapUserToUserDto(userDao.updateUser(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.removeUserById(id);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> allRole = roleDao.getAllRoles();
        return mapListRoleToListRoleDto(allRole);
    }

    private List<RoleDto> mapListRoleToListRoleDto(List<Role> allRole) {
        return allRole.stream().map(role -> RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build()).collect(Collectors.toList());
    }

    private List<UserDto> mapListUserToListUserDto(List<User> allUsers) {
        return allUsers.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .age(user.getAge())
                .mail(user.getMail())
                .roles(mapSetRoleToSetRoleDto(user.getRoles()))
                .build()).collect(Collectors.toList());
    }

    private UserDto mapUserToUserDto(User showUser) {
        return UserDto.builder()
                .id(showUser.getId())
                .username(showUser.getUsername())
                .age(showUser.getAge())
                .mail(showUser.getMail())
                .password(showUser.getPassword())
                .roles(mapSetRoleToSetRoleDto(showUser.getRoles()))
                .build();
    }

    private User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .age(userDto.getAge())
                .mail(userDto.getMail())
                .password(userDto.getPassword())
                .roles(mapSetRoleDtoToSetRole(userDto.getRoles()))
                .build();
    }

    private Set<RoleDto> mapSetRoleToSetRoleDto(Set<Role> roles) {
        return roles.stream().map(role -> RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build()).collect(Collectors.toSet());
    }

    private Set<Role> mapSetRoleDtoToSetRole(Set<RoleDto> rolesDto) {
        return rolesDto.stream().map(role -> Role.builder()
                .id(role.getId())
                .name(role.getName())
                .build()).collect(Collectors.toSet());
    }
}
