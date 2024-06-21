showUserInfo();

let table = document.getElementById("userInfo")

function showUserInfo(user) {
    fetch('http://localhost:8080/api/user')
        .then(response => response.json())
        .then(user => {
            let temp = '';
            table.innerHTML = `<tr> 
                <td> ${user.id} </td>
                <td> ${user.name} </td>
                <td> ${user.lastName} </td>
                <td> ${user.age} </td>
                <td> ${user.email} </td>
                 <td>${user.roles.map(role => role.name).join(' ')}</td> 
            </tr>`;
            data.innerHTML = temp;
            panel.innerHTML = `<h5>${user.username} with roles: ${user.roles.map(role => role.name).join(' ')}</h5>`
        });


}