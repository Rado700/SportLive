document.getElementById('register').addEventListener('click', function () {
    // const type = document.getElementById('userType').value;
    const url = "/login/user/registration";
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
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(data => {
            // Handle success response
            console.log('Success:', data);
            window.location.href = '/account';
        })
        .catch((error) => {
            // Handle error response
            console.error('Error:', error);
        });
});



document.getElementById('enter').addEventListener('click', function () {
    // const type = document.getElementById('userType').value;
    const url = "/login/user/enter";
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
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(data => {
            // Handle success response
            console.log('Success:', data);
            window.location.href = '/account'; // Navigate to /home
        })
        .catch((error) => {
            // Handle error response
            console.error('Error:', error);
        });
});

document.getElementById('register').addEventListener('click', function () {
    // const type = document.getElementById('userType').value;
    const url = "/login/couch/registration";
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
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(data => {
            // Handle success response
            console.log('Success:', data);
            window.location.href = '/couches';
        })
        .catch((error) => {
            // Handle error response
            console.error('Error:', error);
        });
});



document.getElementById('enter').addEventListener('click', function () {
    // const type = document.getElementById('userType').value;
    const url = "/login/couch/enter";
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
                throw new Error('Network response was not ok');
            }
            return response;
        })
        .then(data => {
            // Handle success response
            console.log('Success:', data);
            window.location.href = '/couches'; // Navigate to /home
        })
        .catch((error) => {
            // Handle error response
            console.error('Error:', error);
        });
});