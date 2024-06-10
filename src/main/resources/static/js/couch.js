document.addEventListener('DOMContentLoaded', () => {
    const profileTraining = document.getElementById('profile-training');
    const trainingScreen = document.getElementById('trainingScreen');

    const profileInventory = document.getElementById('profile-inventory');
    const inventoryScreen = document.getElementById('inventoryScreen');

    const selectOrganization = document.getElementById('selectOrganization');
    const statistics = document.getElementById('statistics');
    const addSport = document.getElementById('addSport');

    const showScreen = (screen) => {
        trainingScreen.classList.add('hidden');
        inventoryScreen.classList.add('hidden');
        screen.classList.remove('hidden');
    };


    document.getElementById('addTraining').addEventListener('click', function () {
        showScreen(trainingScreen);
    });

// add training
    profileTraining.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(profileTraining);
        const profileData = {
            place: formData.get('place'),
            description: formData.get('description'),
            date: formData.get('date'),

        };
        fetch('/api/schedule/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(profileData)
        }).then(response => response.json())
            .then(data => alert('Тренировка добавлена: ' + JSON.stringify(data)))
            .catch(error => console.error('Ошибка:', error));

    });

// Add Inventory
    document.getElementById('addInventory').addEventListener('click', function () {
        showScreen(inventoryScreen);
    });

    profileInventory.addEventListener('submit', (ev) => {
        ev.preventDefault();
        const formData = new FormData(profileInventory);
        const profileDate = {
            name: formData.get('name'),
            price: formData.get('price'),
            type: formData.get('type'),
            size: formData.get('size'),
        }

        fetch('/api/inventory/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(profileDate)
        }).then(response => response.json())
            .then(data => alert('Инвентарь добавлен: ' + JSON.stringify(data)))
            .catch(error => console.error('Ошибка:', error));

    });


// Add Exercises
    document.getElementById('addExercises').addEventListener('click', function () {
        const modal = new bootstrap.Modal(document.getElementById('exerciseModal'));
        let exerciseInputs = '';
        for (let i = 1; i <= 5; i++) {
            exerciseInputs += `
            <label for="exercise${i}" class="form-label">Упражнение ${i}</label>
            <input type="text" class="form-control mb-3" id="exercise${i}" required>
        `;
        }
        document.getElementById('exerciseInputs').innerHTML = exerciseInputs;
        modal.show();
    });

//добавить упражнения
    document.getElementById('exerciseForm').addEventListener('submit', function (event) {
        event.preventDefault();
        const exercises = [];
        for (let i = 1; i <= 5; i++) {
            exercises.push(document.getElementById('exercise' + i).value);
        }
        fetch(`$https://sportliveapp.ru/exercises-controller`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({exercises: exercises})
        }).then(response => response.json())
            .then(data => {
                alert('Упражнения добавлены: ' + JSON.stringify(data));
                const modal = bootstrap.Modal.getInstance(document.getElementById('exerciseModal'));
                modal.hide();
            })
            .catch(error => console.error('Ошибка:', error));
    });

    // Select Organization
    document.getElementById('selectOrganization').addEventListener('click', function () {
        const orgName = prompt("Введите название организации:");
        if (orgName) {
            fetch(`${apiUrl}/organisation-controller`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({name: orgName})
            }).then(response => response.json())
                .then(data => alert('Организация выбрана: ' + JSON.stringify(data)))
                .catch(error => console.error('Ошибка:', error));
        }
    });

    // Add Sport
    document.getElementById('addSport').addEventListener('click', function () {
        const sportName = prompt("Введите название вида спорта:");
        if (sportName) {
            fetch(`${apiUrl}/sport-controller`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({name: sportName})
            }).then(response => response.json())
                .then(data => {
                    alert('Вид спорта добавлен: ' + JSON.stringify(data));
                    fetch(`${apiUrl}/booking-controller`)
                        .then(res => res.json())
                        .then(bookings => alert(`Общее количество брони: ${bookings.length}`));
                })
                .catch(error => console.error('Ошибка:', error));
        }
    });

// View Statistics
    document.getElementById('statistics').addEventListener('click', function () {
        Promise.all([
            fetch(`${apiUrl}/user-controller`).then(res => res.json()),
            fetch(`${apiUrl}/inventory-controller`).then(res => res.json()),
            fetch(`${apiUrl}/booking-controller`).then(res => res.json())
        ]).then(data => {
            const [users, inventory, bookings] = data;
            alert(`Статистика:\nПользователи: ${users.length}\nИнвентарь: ${inventory.length}\nБрони: ${bookings.length}`);
        }).catch(error => console.error('Ошибка:', error));
    });


// Stopwatch and Timer
    document.getElementById('stopwatchTimer').addEventListener('click', function () {
        const modal = new bootstrap.Modal(document.getElementById('stopwatchTimerModal'));
        modal.show();
    });

    let stopwatchInterval;
    let stopwatchTime = 0;
    const stopwatchDisplay = document.getElementById('stopwatchDisplay');

    document.getElementById('startStopwatch').addEventListener('click', function () {
        if (stopwatchInterval) return;
        stopwatchInterval = setInterval(() => {
            stopwatchTime++;
            const hours = Math.floor(stopwatchTime / 3600).toString().padStart(2, '0');
            const minutes = Math.floor((stopwatchTime % 3600) / 60).toString().padStart(2, '0');
            const seconds = (stopwatchTime % 60).toString().padStart(2, '0');
            stopwatchDisplay.textContent = `${hours}:${minutes}:${seconds}`;
        }, 1000);
    });

    document.getElementById('stopStopwatch').addEventListener('click', function () {
        clearInterval(stopwatchInterval);
        stopwatchInterval = null;
    });

    document.getElementById('resetStopwatch').addEventListener('click', function () {
        clearInterval(stopwatchInterval);
        stopwatchInterval = null;
        stopwatchTime = 0;
        stopwatchDisplay.textContent = '00:00:00';
    });

    let timerInterval;
    const timerDisplay = document.getElementById('timerDisplay');

    document.getElementById('startTimer').addEventListener('click', function () {
        const timerMinutes = parseInt(document.getElementById('timerMinutes').value);
        if (isNaN(timerMinutes) || timerMinutes <= 0) {
            alert('Введите действительное количество минут.');
            return;
        }
        let timerTime = timerMinutes * 60;
        timerInterval = setInterval(() => {
            if (timerTime <= 0) {
                clearInterval(timerInterval);
                timerDisplay.textContent = '00:00:00';
                alert('Таймер завершен!');
                return;
            }
            timerTime--;
            const minutes = Math.floor(timerTime / 60).toString().padStart(2, '0');
            const seconds = (timerTime % 60).toString().padStart(2, '0');
            timerDisplay.textContent = `00:${minutes}:${seconds}`;
        }, 1000);
    });

    document.getElementById('stopTimer').addEventListener('click', function () {
        clearInterval(timerInterval);
    });

    document.getElementById('resetTimer').addEventListener('click', function () {
        clearInterval(timerInterval);
        timerDisplay.textContent = '00:00:00';
        document.getElementById('timerMinutes').value = '';
    });




});