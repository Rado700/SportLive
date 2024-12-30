let selectedUserType = null; // Глобальная переменная для хранения выбранного типа пользователя

// Получаем все кнопки для выбора типа пользователя
const userTypeButtons = document.querySelectorAll('.user-type-btn');

// Обработчик для выбора типа пользователя
userTypeButtons.forEach(button => {
    button.addEventListener('click', function () {
        // Убираем выделение со всех кнопок
        userTypeButtons.forEach(btn => btn.classList.remove('btn-primary', 'text-white'));
        userTypeButtons.forEach(btn => btn.classList.add('btn-outline-primary'));
        // Добавляем выделение для выбранной кнопки
        this.classList.remove('btn-outline-primary');
        this.classList.add('btn-primary', 'text-white');

        // Устанавливаем выбранный тип пользователя
        selectedUserType = this.getAttribute('data-type');
        console.log("Выбранный тип пользователя:", selectedUserType);
    });
});
console.log(selectedUserType);
// Обработчик для регистрации
document.getElementById('register').addEventListener('click', function () {
    if (selectedUserType === null) {
        alert('Выберите тип пользователя!');
        return;
    }

    const url = "/api/login/" + selectedUserType + "/registration/";
    const name = document.getElementById('name').value;
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
                document.getElementById("error-name").style.display = "block";
                throw new Error("Данный логин занят");
            }
            return response.text();
        })
        .then(data => {
            console.log('Success:', data);
            if (selectedUserType === "user") {
                window.location.href = '/account/user/select';
            } else if (selectedUserType === "couch") {
                window.location.href = '/account/couch/select';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

// Обработчик для входа
document.getElementById('enter').addEventListener('click', function () {
    if (selectedUserType === null) {
        alert('Выберите тип пользователя!');
        return;
    }
    console.log(selectedUserType);
    const url = "/api/login/" + selectedUserType + "/enter/";
    const name = document.getElementById('name').value;
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
                document.getElementById("error-message").style.display = "block";
                throw new Error("Неверный логин или пароль");
            }
            return response.text();
        })
        .then(data => {
            console.log('Success:', data);
            if (selectedUserType === "user") {
                window.location.href = '/account';
            } else {
                window.location.href = '/couches';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});
