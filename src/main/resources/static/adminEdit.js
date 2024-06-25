const form_ed = document.getElementById("formForEditing");
const id_ed = document.getElementById('idE');
const name_ed = document.getElementById('nameE');
const lastName_ed = document.getElementById('lastNameE');
const age_ed = document.getElementById('ageE');
const email_ed = document.getElementById('emailE');
const password_ed = document.getElementById('passwordE');
const roles_ed = document.getElementById('selectRolesE');
const roleAdmin_ed = document.getElementById('roleAdminE');
const roleUser_ed = document.getElementById('roleUserE');

async function editModalData(id) {
    let usersPageEd = await fetch('/api/admin/show/' + id);
    await usersPageEd.json().then(user => {
        id_ed.value = user.id;
        name_ed.value = user.name;
        lastName_ed.value = user.lastName;
        age_ed.value = user.age;
        email_ed.value = user.email;
        password_ed.value = user.password;
        if (user.roles.length === 2) {
            roleAdmin_ed.selected = true;
            roleUser_ed.selected = true;
        } else if (user.roles.length === 1 && (user.roles[0].id === 1)) {
            roleAdmin_ed.selected = true;
            roleUser_ed.selected = false;
        } else if (user.roles.length === 1 && (user.roles[0].id === 2)) {
            roleAdmin_ed.selected = false;
            roleUser_ed.selected = true;
        }
    })
}

form_ed.addEventListener('submit', async (e) => {
    e.preventDefault();
    const roles = roles_ed.selectedOptions;
    let listOfRoles = [];
    for (let i = 0; i < roles.length; i++) {
        listOfRoles.push({
            id: roles[i].value,
            name: "ROLE_" + roles[i].text
        });
    }
    await fetch('/api/admin/update/', {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: id_ed.value,
            name: name_ed.value,
            lastName: lastName_ed.value,
            age: age_ed.value,
            email: email_ed.value,
            password: password_ed.value,
            roles: listOfRoles,
        })
    }).then(() => {
        findAll();
        $("#editCloseBtn").click();
    })
})