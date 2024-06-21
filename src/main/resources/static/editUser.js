$(async function() {
    editUser();

});
function editUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRoles = [];
        if (editForm.roles !== undefined) {
            for (let i = 0; i < editForm.roles.options.length; i++) {
                if (editForm.roles.options[i].selected) editUserRoles.push({
                    id: editForm.roles.options[i].value,
                    name: "ROLE_" + editForm.roles.options[i].text
                })
            }
        }

        fetch("http://localhost:8080/api/users/" + editForm.id.value, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                name: editForm.name.value,
                surname: editForm.surname.value,
                age: editForm.age.value,
                email: editForm.email.value,
                roles: editUserRoles,
                password: editForm.password.value

            })
        }).then(() => {
            $('#editFormCloseButton').click();
            allUsers();
        })
    })
}