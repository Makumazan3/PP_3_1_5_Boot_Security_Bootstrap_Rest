const form_del = document.getElementById('formForDeleting');
const name_del = document.getElementById('firstNameD');
const lastName_del = document.getElementById('lastNameD');
const age_del = document.getElementById('ageD');
const username_del = document.getElementById('emailD');
const roleAdmin_del = document.getElementById('roleAdminD');
const roleUser_del = document.getElementById('roleUserD');
var id_del = 0;

async function deleteModalData(id) {
    const urlForDel = '/api/admin/show/' + id;
    let usersPageDel = await fetch(urlForDel);
    await usersPageDel.json().then(user => {
        id_del = user.id;
        name_del.value = user.name;
        lastName_del.value = user.lastName;
        age_del.value = user.age;
        username_del.value = user.email;
        if (user.roles.length === 2) {
            roleAdmin_del.selected = true;
            roleUser_del.selected = true;
        } else if (user.roles.length === 1 && (user.roles[0].id === 1)) {
            roleAdmin_del.selected = true;
            roleUser_del.selected = false;
        } else if (user.roles.length === 1 && (user.roles[0].id === 2)) {
            roleAdmin_del.selected = false;
            roleUser_del.selected = true;
        }
    })
}

form_del.addEventListener('submit', async (e) => {
    e.preventDefault();
    let url = '/api/admin/delete/' + id_del;
    await fetch(url, {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }).then(() => {
        findAll();
        $("#deleteCloseBtn").click();
    });
})