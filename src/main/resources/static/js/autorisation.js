document.getElementById('register').addEventListener('click', function () {
    const type = document.getElementById('userType').value;
    const url = "/login/"+type+"/registration";
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name: name, password: password})
    })
        .then(response => {
            if (!response.ok) {
                document.getElementById("error-name").style.display = "block";
                throw new Error("Данный логин занят")

            }
            return response;
        })
        .then(data => {

                // Handle success response
                console.log('Success:', data);
            if (type === "user") {
                window.location.href = '/account';
            }else {
                window.location.href = '/couches';
            }
        })

        .catch((error) => {
            // Handle error response
            console.error('Error:', error);
        });
});



document.getElementById('enter').addEventListener('click', function () {
    let type = document.getElementById('userType').value;
    const url = "/login/"+type+"/enter";
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name: name, password: password})
    })
        .then(response => {
            if (!response.ok) {
                document.getElementById("error-message").style.display = "block";
                throw new Error("Неверный логин или пароль")
            }
            return response;
        })
        .then(data => {
            // Handle success response
            console.log('Success:', data);
            if (type === "user") {
                window.location.href = '/account';
            }else {
                window.location.href = '/couches';
            }
        })

        .catch((error) => {
            // Handle error response
            console.error('Error:', error);
        });
});
