const url = 'http://localhost:8080/api/admin/users'
const t_body = document.getElementById(`tbody`)

async function findAll() {
    let adminPage = await fetch(url)
    let userList = await adminPage.json();
    let users_table = '';
    for (let user of userList) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.name.toString().replaceAll('ROLE_', ''))
        }
        users_table +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${roles}</td>
                <td>
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#editModal" onclick="editModalData(${user.id})">Edit</button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-toggle="modal" 
                        data-target="#deleteModal" onclick="deleteModalData(${user.id})">Delete</button>
                </td>
            </tr>`
    }
    t_body.innerHTML = users_table;
}

findAll();