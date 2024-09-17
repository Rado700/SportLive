document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('my-container')

    const profileTraining = document.getElementById('profile-training');
    const trainingScreen = document.getElementById('trainingScreen');

    const profileInventory = document.getElementById('profile-inventory');
    const inventoryScreen = document.getElementById('inventoryScreen');

    const infoScreen = document.getElementById('info-screen');
    // const statistics = document.getElementById('statistics');
    const refreshButton = document.getElementById('refresh-data');


    // const selectOrganization = document.getElementById('selectOrganization');
    // const addSport = document.getElementById('addSport');
    // const addCouch = document.getElementById('addCouch');

    const trainerInfo = document.getElementById('trainer-info');
    const profileInfo = document.getElementById('profile-info');
    const equipmentInfo = document.getElementById('equipment-info');

    const showScreen = (screen) => {
        // trainingScreen.classList.add('hidden');
        // inventoryScreen.classList.add('hidden');
        infoScreen.classList.add('hidden');
        screen.classList.remove('hidden');
    };


    document.getElementById('addTraining').addEventListener('click', function () {
        fetch("/api/couch/sport-section/")
            .then(response =>{
                if (!response.ok){
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
                data.forEach(sportSection =>{
                const sportSectionSelect = document.getElementById("sportSections");
                const option = document.createElement("option");
                option.value = sportSection.id;
                option.textContent = sportSection.name;
                sportSectionSelect.appendChild(option);
                })
            showScreen(trainingScreen);
        })

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
        const sportSectionSelect = document.getElementById("sportSections");
        const sportSectionId = parseInt(sportSectionSelect.value);

        fetch('/api/schedule/'+sportSectionId, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(profileData)
        }).then(response => response.json())
            .then(data => {
                alert('Тренировка добавлена: ' + JSON.stringify(data))
                const modal = new bootstrap.Modal(document.getElementById('trainingModal'));
                modal.hide();
            })
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

        fetch('/api/inventory/couch/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(profileDate)
        }).then(response => response.json())
            .then(data => alert('Инвентарь добавлен: ' + JSON.stringify(data)))
            .catch(error => console.error('Ошибка:', error));

    });

    document.getElementById('statistics').addEventListener('click', () => {
        showScreen(infoScreen);

        const fetchData = () => {
            fetch('/api/inventory/couchInventory/')
                .then(response => response.json())
                .then(data => {
                    equipmentInfo.innerHTML = JSON.stringify(data, null, 2);
                })
                .catch(error => {
                    equipmentInfo.innerHTML = 'Ошибка загрузки данных инвентаря: ' + error;
                });

            fetch('/api/booking/couchBooking/')
                .then(response => response.json())
                .then(data => {
                    trainerInfo.innerHTML = JSON.stringify(data, null, 2);
                })
                .catch(error => {
                    trainerInfo.innerHTML = 'Ошибка загрузки данных расписания: ' + error;
                });

            fetch('/api/couch/getCouch/')
                .then(response => response.json())
                .then(data => {
                    profileInfo.innerHTML = JSON.stringify(data, null, 2);
                })
                .catch(error => {
                    profileInfo.innerHTML = 'Ошибка загрузки данных авторизации: ' + error;
                });
        };

        refreshButton.addEventListener('click', fetchData);

        fetchData();
    })

    document.getElementById('back-to-main-info').addEventListener('click', () => {
        window.location.href = "/";
    });


    // // Select Organization
    // document.getElementById('selectOrganization').addEventListener('click', function () {
    //     const orgName = prompt("Введите название организации:");
    //     if (orgName) {
    //         fetch(`${apiUrl}/organisation-controller`, {
    //             method: 'POST',
    //             headers: {'Content-Type': 'application/json'},
    //             body: JSON.stringify({name: orgName})
    //         }).then(response => response.json())
    //             .then(data => alert('Организация выбрана: ' + JSON.stringify(data)))
    //             .catch(error => console.error('Ошибка:', error));
    //     }
    // });

    // // Add Sport
    // document.getElementById('addSport').addEventListener('click', function () {
    //     const sportName = prompt("Введите название вида спорта:");
    //     if (sportName) {
    //         fetch(`${apiUrl}/sport-controller`, {
    //             method: 'POST',
    //             headers: {'Content-Type': 'application/json'},
    //             body: JSON.stringify({name: sportName})
    //         }).then(response => response.json())
    //             .then(data => {
    //                 alert('Вид спорта добавлен: ' + JSON.stringify(data));
    //                 fetch(`${apiUrl}/booking-controller`)
    //                     .then(res => res.json())
    //                     .then(bookings => alert(`Общее количество брони: ${bookings.length}`));
    //             })
    //             .catch(error => console.error('Ошибка:', error));
    //     }
    // });


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

function exit() {

    fetch("/api/login/couch/exit")
        .then(response => {
            if (response.ok) {
                window.location.href = "/";
            }
        }).catch(error => console.error('Error:', error))
}