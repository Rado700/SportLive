document.getElementById('register').addEventListener('click', function () {
    const type = document.getElementById('userType').value;
    const url = "https://sportliveapp.ru/authorisation/" + type + "/registration";
    const name = document.getElementById('Name').value;
    const password = document.getElementById('password').value;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: name, password: password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Handle success response
        console.log('Success:', data);
        window.location.href = '/mvp/user.html?'; // Navigate to /home
    })
    .catch((error) => {
        // Handle error response
        console.error('Error:', error);
    });
});



document.getElementById('enter').addEventListener('click', function () {
    const type = document.getElementById('userType').value;
    const url = "https://sportliveapp.ru/authorisation/" + type + "/enter";
    const name = document.getElementById('Name').value;
    const password = document.getElementById('password').value;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: name, password: password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Handle success response
        console.log('Success:', data);
        window.location.href = '/mvp/user.html?'; // Navigate to /home
    })
    .catch((error) => {
        // Handle error response
        console.error('Error:', error);
    });
});