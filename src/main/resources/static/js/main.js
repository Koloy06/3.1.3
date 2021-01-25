let modal = $('#defaultModal');
let modalTitle = $('.modal-title');
let modalBody = $('.modal-body');
let modalFooter = $('.modal-footer');

let clearFormButton = $('<button type="reset" class="btn btn-secondary">Clear</button>');
let primaryButton = $('<button type="button" class="btn btn-primary"></button>');
let dismissButton = $('<button type="button" class="btn btn-secondary" data-dismiss="modal"></button>');
let dangerButton = $('<button type="button" class="btn btn-danger"></button>');

$(document).ready(function(){
    viewAllUsers();
    defaultModal();
});

function defaultModal() {
    modal.modal({
        keyboard: true,
        backdrop: "static",
        show: false,
    }).on("show.bs.modal", function(event){
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let action = button.data('action');
        switch(action) {
            case 'editUser':
                editUser($(this), id);
                break;

            case 'deleteUser':
                deleteUser($(this), id);
                break;
        }
    }).on('hidden.bs.modal', function(event){
        $(this).find('.modal-title').html('');
        $(this).find('.modal-body').html('');
        $(this).find('.modal-footer').html('');
    });
}

async function viewAllUsers() {
    $('#userTable tbody').empty();
    const usersResponse = await userService.findAll();
    const usersJson = usersResponse.json();

    usersJson.then(users => {
        users.forEach(user => {
            let stringRole = '';
            user.roles.forEach(role =>
                stringRole += role.name.replace('ROLE_', '') + ' '
            );
            stringRole = stringRole.trim();
            let userRow = `$(<tr>
                        <th scope="row">${user.id}</th>
                        <td>${user.username}</td>
                        <td>${user.age}</td>
                        <td>${user.mail}</td>
                        <td>${stringRole}</td>
                        <td class="text-center">
                            <button class="btn btn-info" data-id="${user.id}" data-action="editUser" data-toggle="modal" data-target="#defaultModal">Edit</button>
                        </td>
                        <td class="text-center">
                            <button class="btn btn-danger" data-id="${user.id}" data-action="deleteUser" data-toggle="modal" data-target="#defaultModal">Delete</button>
                        </td>
                    </tr>)`;
            $('#userTable tbody').append(userRow);
        });
    });
}

async function editUser(modal, id) {
    const userResponse = await userService.findById(id);
    const userJson = userResponse.json();
    const rolesAllResponse = await userService.findAllRoles();
    const rolesAllJson = rolesAllResponse.json();

    let idInput = `<div class="form-group">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" disabled>
            <div class="invalid-feedback"></div>
        </div>`;

    modal.find(modalTitle).html('Edit User');
    let userFormHidden = $('.userForm:hidden')[0];
    modal.find(modalBody).html($(userFormHidden).clone());
    let userForm = modal.find('.userForm');
    userForm.prop('id', 'updateUserForm');
    modal.find(userForm).prepend(idInput);
    modal.find(userForm).show();
    dismissButton.html('Cancel');
    modal.find(modalFooter).append(dismissButton);
    primaryButton.prop('id', 'updateUserButton');
    primaryButton.html('Update');
    modal.find(modalFooter).append(primaryButton);

    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#username').val(user.username);
        modal.find('#age').val(user.age);
        modal.find('#mail').val(user.mail);
        modal.find('#roles').val(rolesAllJson);
        rolesAllJson.then(roles => {
            roles.forEach(role => {
                let flag = false;
                user.roles.forEach(roleUser => {
                    if (roleUser.id == role.id) {
                        flag = true;
                    }
                });
                if (flag)
                    modal.find('#roles').append(new Option(role.name.replace('ROLE_', ''), role.id, false, true));
                else
                    modal.find('#roles').append(new Option(role.name.replace('ROLE_', ''), role.id));
            });
        });
    });


    $('#updateUserButton').click(async function(e){
        let id = userForm.find('#id').val().trim();
        let username = userForm.find('#username').val().trim();
        let age = userForm.find('#age').val().trim();
        let password = userForm.find('#password').val().trim();
        let mail = userForm.find('#mail').val().trim();
        let rolesArrayId = userForm.find('#roles').val();
        let rolesArrayJsonId = rolesArrayId.map(function (id) {
            return{id: id}
        });
        let data = {
            id: id,
            username: username,
            age: age,
            password: password,
            mail: mail,
            roles: rolesArrayJsonId
        };
        console.log(data)

        const userResponse = await userService.update(data);

        if (userResponse.status == 200) {
            viewAllUsers();
            modal.find('.modal-title').html('Success');
            modal.find('.modal-body').html('User updated!');
            dismissButton.html('Close');
            modal.find(modalFooter).html(dismissButton);
            $('#defaultModal').modal('show');
        } else if (userResponse.status == 400) {
            userResponse.json().then(response => {
                response.validationErrors.forEach(function(error){
                    modal.find('#' + error.field).addClass('is-invalid');
                    modal.find('#' + error.field).next('.invalid-feedback').text(error.message);
                });
            });
        } else {
            userResponse.json().then(response => {
                let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert">
                        ${response.error}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>`;
                modal.find('.modal-body').prepend(alert);
            });
        }
    });
}

async function deleteUser(modal, id) {
    const userResponse = await userService.findById(id);
    const userJson = userResponse.json();

    modal.find(modalTitle).html('Delete User');
    let userTableHidden = $('.userTable:hidden')[0];
    modal.find(modalBody).html($(userTableHidden).clone());
    let userTable = modal.find('.userTable');
    modal.find(userTable).show();
    dismissButton.html('Close');
    modal.find(modalFooter).append(dismissButton);
    dangerButton.prop('id', 'deleteUserButton');
    dangerButton.html('Delete');
    modal.find(modalFooter).append(dangerButton);

    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#username').val(user.username);
        modal.find('#age').val(user.age);
        modal.find('#mail').val(user.mail);
        user.roles.forEach(roleUser => {
            let flag = false;
            if (roleUser) {
                flag = true;
            if (flag)
                modal.find('#roles').append(new Option(roleUser.name.replace('ROLE_', ''), roleUser.id, false, true));
            else
                modal.find('#roles').append(new Option(roleUser.name.replace('ROLE_', ''), roleUser.id));
            }
        });
    });

    $('#deleteUserButton').click(async function(e){
        const userResponse = await userService.delete(id);

        if (userResponse.status == 200) {
            viewAllUsers();
            modal.find('.modal-title').html('Success');
            modal.find('.modal-body').html('User deleted!');
            dismissButton.html('Close');
            modal.find(modalFooter).html(dismissButton);
            $('#defaultModal').modal('show');
        } else {
            userResponse.json().then(response => {
                let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert">
                            ${response.error}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
                modal.find('.modal-body').prepend(alert);
            });
        }
    });
}

const http = {
    fetch: async function(url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });

        return response;
    }
};

const userService = {
    findAll: async () => {
        return await http.fetch('/api/v1/users');
    },
    add: async (data) => {
        return await http.fetch('/api/v1/users/create', {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },
    findById: async (id) => {
        return await http.fetch('/api/v1/users/' + id);
    },
    update: async (data) => {
        return await http.fetch('/api/v1/users/update', {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    },
    delete: async (id) => {
        return await http.fetch('/api/v1/users/delete/' + id, {
            method: 'DELETE'
        });
    },
    findAllRoles: async () => {
        return await http.fetch('/api/v1/roles');
    },
};