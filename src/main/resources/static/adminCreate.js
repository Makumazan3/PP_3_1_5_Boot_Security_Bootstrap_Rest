const form_new = document.forms["formForCreatingNewUser"];
const roles_new = document.querySelector('#roles').selectedOptions;
form_new.addEventListener("submit", ev => {
    ev.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < roles_new.length; i++) {
        listOfRole.push({
            id: roles_new[i].value,
            name: "ROLE_" + roles_new[i].text
        });
        console.log(listOfRole);
    }
    fetch('/api/admin/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: form_new.name.value,
            lastName:  form_new.lastName.value,
            age: form_new.age.value,
            email: form_new.email.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }).then(() => {
        form_new.reset();
        findAll();
        $('#nav-home-tab').click();
    });
})