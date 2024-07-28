document.addEventListener('DOMContentLoaded', () => {
    const mainScreen = document.getElementById('main-screen');
    const profileScreen = document.getElementById('profile-screen');
    const infoScreen = document.getElementById('info-screen');
    const stopwatchScreen = document.getElementById('stopwatch-screen');
    const recordForSection = document.getElementById('recordForSection');


    const profileForm = document.getElementById('profile-form');
    const profileInfo = document.getElementById('profile-info span');
    const trainerInfo = document.getElementById('trainer-info span');
    const equipmentInfo = document.getElementById('equipment-info span');

    const addEquipment = document.getElementById('add-equipment');
    const addActivity = document.getElementById('add-activity');
    const addBalance = document.getElementById('add-balance');


    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');
    const height = document.getElementById('height');
    const weight = document.getElementById('weight');

    const login = document.getElementById('login');

    const newLoginMessage = document.getElementById('newLogin');

    const allSportSection = document.getElementById("sports-section");
    const allCouchForSportSection = document.getElementById("coach");


    const stopwatchDisplay = document.getElementById('stopwatch-display');
    let stopwatchInterval;
    let stopwatchSeconds = 0;

    const timerDisplay = document.getElementById('timer-display');

    let timerInterval;

    const showScreen = (screen) => {
        mainScreen.classList.add('hidden');
        profileScreen.classList.add('hidden');
        infoScreen.classList.add('hidden');
        stopwatchScreen.classList.add('hidden');
        recordForSection.classList.add('hidden');
        screen.classList.remove('hidden');
    };

    document.getElementById('profile').addEventListener('click', () => {
        showScreen(profileScreen);
        fetch("/api/user/")
            .then((response) => {
                if (!response.ok) {
                    console.error("Нету профиля")
                }
                return response.json()
            }).then(data => {
            firstName.value = `${data.name}`;
            lastName.value = `${data.surname}`;
            height.value = `${data.height}`;
            weight.value = `${data.weight}`;

        });
        fetch("/api/login/")
            .then((response) => {
                if (!response.ok) {
                    console.error("Не авторизован")
                }
                return response.json();
            })
            .then(data => {
                login.value = `${data.login}`;
            })

    });

    document.getElementById('info').addEventListener('click', () => {
        showScreen(infoScreen);


        fetch('/api/info/')
            .then(response => response.json())
            .then(data => {
                profileInfo.textContent = `${data.name} ${data.surname}, ${data.height} cm, ${data.weight} kg (Дата обновления: ${data.date})`;
                trainerInfo.textContent = data.remainingSessions;
                equipmentInfo.textContent = data.equipmentCount;
            });
    });
    // ЗАПИСЬ НА ТРЕНЕРОВКУ

    addActivity.addEventListener('click', () => {
        showScreen(recordForSection);

        const url = "/api/user/"
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Не авторизован")
                }
                return response.json();
            }).then(data => {
            const allSportSectionDB = data.selectedSportSections;
            allSportSection.innerHTML = "<option value=\"SportSection\"  disabled selected hidden>Спортиваная секция</option>";
            if (allSportSectionDB === []) {
                alert("not sportSection");
            }
            allSportSectionDB.forEach(item => {
                const option = document.createElement("option");
                option.value = item.id;
                option.textContent = item.name;
                allSportSection.appendChild(option);
            })
            return data;
        })
            .then(data => {
                const allCouchForSportSectionDB = data.selectedCouches;
                allCouchForSportSection.innerHTML = "<option value=\"couch\"  disabled selected hidden>Тренер</option>";
                if (allCouchForSportSectionDB === []) {
                    alert("notCouches");
                }
                allCouchForSportSectionDB.forEach(item => {
                    const option = document.createElement('option');
                    option.value = item.id;
                    option.textContent = item.name;
                    allCouchForSportSection.appendChild(option);
                })
            })
    })

    addEquipment.addEventListener('submit', () => {

        const url = "/api/inventory/couchInventory/"
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Нету такого инвентаря тренера")
                }
                return response.json();
            })
            .then(data => {
                function setEquipment(data) {
                    const getAllEquipment = document.getElementById("add-equipment");
                    data.forEach(item => {
                        const option = document.createElement("option");
                        option.value = item.id;
                        option.textContent = item.name;
                        option.textContent = item.price;
                        option.textContent = item.size;
                        option.textContent = item.type;
                        getAllEquipment.appendChild(option);
                    })
                }

                const getAllEquipment = document.getElementById("add-equipment")
                const equipmentId = getAllEquipment.value

                fetch("api/inventory/couch/" + equipmentId)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Неверная организация")
                        }
                        return response.json();
                    })
                    .then(data => setEquipment(data))
                    .catch(error => console.error(error));
            })
    });


    document.getElementById('stopwatch').addEventListener('click', () => {
        showScreen(stopwatchScreen);
    });

    profileForm.addEventListener('submit', (e) => {
        e.preventDefault();
        newLoginMessage.style.display = 'none';
        const formData = new FormData(profileForm);
        const profileData = {
            name: formData.get('firstName'),
            surname: formData.get('lastName'),
            height: formData.get('height'),
            weight: formData.get('weight')
        };
        console.log(profileData);
        // Send data to backend
        fetch('/api/user/', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(profileData)
        })
            .then(response => response.json())

            .then(data => {
                const profileData2 = {
                    name: formData.get('login'),
                    password: formData.get('password')
                };
                console.log(profileData2);
                fetch("/api/login/user/", {
                    method: 'PUT',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify(profileData2)
                }).then(response => {
                    if (response.status === 400) {
                        newLoginMessage.style.display = 'block';
                        return;
                    }
                    if (!response.ok) {
                        console.error("Проверить данные ввода")
                    }
                    alert('Данные профиля обновлены');
                    return response.json();
                })

            });
    });

    // Stopwatch functionality
    document.getElementById('start-stopwatch').addEventListener('click', () => {
        if (stopwatchInterval) clearInterval(stopwatchInterval);
        stopwatchInterval = setInterval(() => {
            stopwatchSeconds++;
            let hours = Math.floor(stopwatchSeconds / 3600);
            let minutes = Math.floor((stopwatchSeconds % 3600) / 60);
            let seconds = stopwatchSeconds % 60;
            stopwatchDisplay.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
        }, 1000);
    });

    document.getElementById('stop-stopwatch').addEventListener('click', () => {
        clearInterval(stopwatchInterval);
    });

    // Timer functionality
    document.getElementById('start-timer').addEventListener('click', () => {
        const days = parseInt(document.getElementById('timer-days').value) || 0;
        const hours = parseInt(document.getElementById('timer-hours').value) || 0;
        const minutes = parseInt(document.getElementById('timer-minutes').value) || 0;
        const seconds = parseInt(document.getElementById('timer-seconds').value) || 0;
        let totalSeconds = (days * 24 * 3600) + (hours * 3600) + (minutes * 60) + seconds;

        if (timerInterval) clearInterval(timerInterval);
        timerInterval = setInterval(() => {
            if (totalSeconds <= 0) {
                clearInterval(timerInterval);
                timerDisplay.textContent = "00:00:00:00";
                return;
            }
            totalSeconds--;
            let days = Math.floor(totalSeconds / (24 * 3600));
            let hours = Math.floor((totalSeconds % (24 * 3600)) / 3600);
            let minutes = Math.floor((totalSeconds % 3600) / 60);
            let seconds = totalSeconds % 60;
            timerDisplay.textContent = `${String(days).padStart(2, '0')}:${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
        }, 1000);
    });

    document.getElementById('save-notes').addEventListener('click', () => {
        const notes = document.getElementById('notes').value;
        // Send notes to backend
        fetch('/api/notes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({notes})
        })
            .then(response => response.json())
            .then(data => {
                alert('Заметки сохранены');
                document.getElementById('notes').value = '';
            });
    });


    // Back to main screen buttons
    document.getElementById('back-to-main-profile').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-info').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-stopwatch').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-record').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    const currentMonth = new Date().getMonth();
    document.getElementById('month').value = currentMonth;
    generateCalendar(currentMonth);
});


function exit() {

    fetch("/api/login/user/exit")
        .then(response => {
            if (response.ok) {
                window.location.href = "/";
            }
        }).catch(error => console.error('Error:', error))
}

document.getElementById("month").addEventListener('change',function (){
    generateCalendar(this.value);
})

function generateCalendar(month) {

    const calendar = document.getElementById('calendar');
    calendar.innerHTML = '';
    let weekDays = ["Пн","Вт","Ср","Чт","Пт","Сб","Вс"];
    for (let i = 0; i < 7; i++){
        const emptySlot = document.createElement('div');
        emptySlot.textContent = weekDays[i];
        calendar.appendChild(emptySlot);
    }
    const date = new Date();
    date.setMonth(month);
    date.setDate(1);
    let firstDay = date.getDay()-1;
    if (firstDay < 0){
        firstDay = 6;
    }
    const year = date.getFullYear();
    const currentMonth = date.getMonth();
    const firstDayOfNextMonth = new Date(year, currentMonth + 1, 1);
    const daysInMonth = new Date(firstDayOfNextMonth - 1).getDate();


    //const  = new Date(date.getFullYear(), month + 1, 0).getDate();
    console.log(firstDay)
    console.log(daysInMonth)
    // Fill initial empty slots
    for (let i = 0; i < firstDay; i++) {
        const emptySlot = document.createElement('div');
        calendar.appendChild(emptySlot);
    }

    // Create buttons for each day of the month
    for (let day = 1; day <= daysInMonth; day++) {
        const dayButton = document.createElement('button');
        dayButton.textContent = day;
        dayButton.addEventListener('click', function () {
            // Deselect previously selected day
            const previouslySelected = document.querySelector('.calendar button.selected');
            if (previouslySelected) {
                previouslySelected.classList.remove('selected');
            }
            // Select the clicked day
            dayButton.classList.add('selected');
        });
        calendar.appendChild(dayButton);
    }
}

