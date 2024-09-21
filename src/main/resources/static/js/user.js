document.addEventListener('DOMContentLoaded', () => {
    const mainScreen = document.getElementById('main-screen');
    const profileScreen = document.getElementById('profile-screen');
    const infoScreen = document.getElementById('info-screen');
    const stopwatchScreen = document.getElementById('stopwatch-screen');
    const recordForSection = document.getElementById('recordForSection');
    const recordForEquipment = document.getElementById('recordForEquipment');
    const chooseTrainer = document.getElementById('choose-trainer');
    const changeToCoach = document.getElementById('changeToCoach');


    const profileForm = document.getElementById('profile-form');
    const profileInfo = document.getElementById('profile-info');
    const trainerInfo = document.getElementById('trainer-info');
    const trainerRest = document.getElementById('trainer-rest');
    const equipmentInfo = document.getElementById('equipment-info');

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
    const coachForInventory = document.getElementById("coachForInventory");
    const coachForUser = document.getElementById("coachForUser");


    const getAllEquipment = document.getElementById('getAllEquipment')

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
        recordForEquipment.classList.add('hidden');
        changeToCoach.classList.add('hidden');
        screen.classList.remove('hidden');
    };

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    if (urlParams.get("page") === 'recordScreen') {
        openRecordScreenWithData(urlParams.get('couch'));
    }


    //Заменить тренера у пользователя
    chooseTrainer.addEventListener('click', () => {
        showScreen(changeToCoach);

        fetch("/api/user/couch/", {
            method: "GET",
            headers: {'Content-Type': 'application/json'},
        }).then(response => {
            if (!response.ok) {
                throw new Error(response.message);
            }
            return response.json();
        }).then(getData => {
            getAllCouch(getData);
        })

        // fetch("/api/user/")
        //     .then(response=>{
        //         if (!response.ok){
        //             console.error("нету такого пользователя")
        //         }
        //         return response.json()
        //     })
        //     .then(data=>{
        //         const coachChangeDB = data.selectedCouches;
        //         coachForUser.innerHTML = "<option value=\"coachForUser\" disabled selected hidden></option>";
        //         coachChangeDB.forEach(item => {
        //             const option = document.createElement("option")
        //             option.value = item.id;
        //             option.name = item.name;
        //             coachForUser.appendChild(option);
        //         })
        //
        // });

        // function enterForCoach() {
        //     fetch("/api/user/couch/" + coachForUser.value, {
        //         method: 'POST',
        //         headers: {"Content-Type": "application/json"},
        //     })
        //         .then(response => {
        //             if (!response.ok) {
        //                 alert("нету такого тренера")
        //             }
        //         }).then(data => {getAllCouch(data)})
        //
        // }
        function getAllCouch(getData) {
            const coach = document.getElementById("getAllCoaches");
            coach.innerHTML = "";

            if (getData.length === 0) {
                coach.innerHTML = '<p>Нету выбранных тренеров</p>';
                return;
            }

            getData.forEach(item => {
                const getCoach = document.createElement("div");
                getCoach.classList.add("coach-container");

                // Создаем контейнер для фотографии и информации
                const coachInfo = document.createElement("div");
                coachInfo.classList.add("coach-info");

                // Фотография тренера
                const photo = document.createElement("img");
                photo.src = item.photo || '/coach/photo/default.jpg';
                photo.alt = `${item.name}`;
                photo.classList.add("coach-photo");
                coachInfo.appendChild(photo);

                // Контейнер для текста (имя, стаж, виды спорта)
                const textInfo = document.createElement("div");
                textInfo.classList.add("text-info");

                // Имя тренера
                const name = document.createElement("p");
                name.textContent = `${item.name}`;
                textInfo.appendChild(name);

                // Стаж тренера
                const experience = document.createElement("p");
                experience.textContent = `Опыт: ${item.experience || 'Неизвестно'}`;
                textInfo.appendChild(experience);

                // Добавляем информацию в coachInfo
                coachInfo.appendChild(textInfo);

                // Контейнер для кнопок
                const buttonsContainer = document.createElement("div");
                buttonsContainer.classList.add("buttons-container");

                // Кнопка "Выбрать"
                const selectButton = document.createElement("button");
                selectButton.textContent = "Выбрать";
                selectButton.onclick = () => {
                    window.location.href = 'account?page=recordScreen&couch=' + item.name
                }
                selectButton.classList.add("select-button");
                buttonsContainer.appendChild(selectButton);

                // Кнопка "Удалить"
                const deleteButton = document.createElement("button");
                deleteButton.textContent = "Удалить";
                deleteButton.classList.add("delete-button");
                deleteButton.addEventListener("click", () => {
                    fetch(`/api/user/couch/${item.id}`, {
                        method: 'DELETE'
                    })
                        .then(response => {
                            if (response.ok) {
                                getCoach.remove(); // Удаляем элемент из DOM
                            } else {
                                throw new Error(response.message);
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            alert('Failed to delete the coach.');
                        });
                });
                buttonsContainer.appendChild(deleteButton);

                // Добавляем все элементы в главный контейнер getCoach
                getCoach.appendChild(coachInfo);
                getCoach.appendChild(buttonsContainer);

                // Добавляем getCoach в DOM
                coach.appendChild(getCoach);
            });
        }
    })


    // Отображение Данных пользователя
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
    // Отображение Общей информаций
    document.getElementById('info').addEventListener('click', () => {
        showScreen(infoScreen);


        const profileInfo = document.getElementById('profile-info').querySelector('span');
        const trainerInfo = document.getElementById('trainer-info').querySelector('span');
        const equipmentInfo = document.getElementById('equipment-info').querySelector('span');


        fetch('/api/user/')
            .then(response => response.json())
            .then(data => {
                profileInfo.textContent = `${data.name} ${data.surname}, ${data.height} cm, ${data.weight} kg`;
            });


        fetch('/api/user/couch/', {
            method: 'GET',
            headers: {'Content-type': 'application/json'},
        })
            .then(response => response.json())
            .then(data => {
                trainerInfo.textContent = JSON.stringify(data, null, 2);
            });


        fetch('/api/inventory/userInventory/', {
            method: 'GET',
            headers: {'Content-type': 'application/json'},
        })
            .then(response => response.json())
            .then(data => {
                getEquipment(data)
            });
    });

    function getEquipment(getData) {
        const container = document.getElementById('equipment-info')
        container.innerHTML = '';
        if (getData.length === 0) {
            container.innerHTML = '<p>No equipment available</p>';
            return;
        }
        getData.forEach(item => {
            const newElement = document.createElement("div");
            newElement.classList.add("equipment-item");

            const name = document.createElement("h3");
            name.textContent = item.name;
            newElement.appendChild(name);

            const type = document.createElement("p");
            type.textContent = item.type;
            newElement.appendChild(type);

            const price = document.createElement("p");
            price.textContent = item.price;
            newElement.appendChild(price);

            const size = document.createElement("p");
            size.textContent = item.size;
            newElement.appendChild(size);

            container.appendChild(newElement);

        })
    }

    // document.getElementById('info').addEventListener('click', () => {
    //     showScreen(infoScreen);
    //
    //     const profileInfo = document.getElementById('profile-info').querySelector('span');
    //     const trainerInfo = document.getElementById('trainer-info').querySelector('span');
    //     const equipmentContainer = document.getElementById('equipment-info');
    //
    //     // Получаем данные профиля пользователя
    //     fetch('/api/user/')
    //         .then(response => response.json())
    //         .then(data => {
    //             profileInfo.textContent = `${data.name} ${data.surname}, ${data.height} cm, ${data.weight} kg`;
    //         })
    //         .catch(error => console.error('Error fetching user data:', error));
    //
    //     // Получаем данные тренера
    //     fetch('/api/user/couch/', {
    //         method: 'GET',
    //         headers: {'Content-Type': 'application/json'},
    //     })
    //         .then(response => response.json())
    //         .then(data => {
    //             trainerInfo.textContent = data.length > 0 ? `Trainer: ${data[0].name}, Experience: ${data[0].couchInfo.experience || 'No experience'}`
    //                 : 'No trainer assigned.';
    //         })
    //         .catch(error => console.error('Error fetching trainer data:', error));
    //
    //     // Получаем данные инвентаря
    //     fetch('/api/inventory/userInventory/', {
    //         method: 'GET',
    //         headers: {'Content-Type': 'application/json'},
    //     })
    //         .then(response => response.json())
    //         .then(data => {
    //             getEquipment(data);
    //         })
    //         .catch(error => console.error('Error fetching equipment data:', error));
    // });
    //
    // function getEquipment(getData) {
    //     const container = document.getElementById('equipment-info');
    //     container.innerHTML = '';
    //
    //     if (getData.length === 0) {
    //         container.innerHTML = '<p>No equipment available</p>';
    //         return;
    //     }
    //
    //     getData.forEach(item => {
    //         const equipmentItem = document.createElement('div');
    //         equipmentItem.classList.add('equipment-item');
    //
    //         const name = document.createElement('h3');
    //         name.textContent = item.name;
    //         equipmentItem.appendChild(name);
    //
    //         const type = document.createElement('p');
    //         type.innerHTML = `<strong>Type:</strong> ${item.type}`;
    //         equipmentItem.appendChild(type);
    //
    //         const price = document.createElement('p');
    //         price.innerHTML = `<strong>Price:</strong> $${item.price}`;
    //         equipmentItem.appendChild(price);
    //
    //         const size = document.createElement('p');
    //         size.innerHTML = `<strong>Size:</strong> ${item.size}`;
    //         equipmentItem.appendChild(size);
    //
    //         container.appendChild(equipmentItem);
    //     });
    // }

    //Записаться на тренеровку(выбор спортсекций и тренера)

    function openRecordScreenWithData(couchName) {
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
            allSportSection.innerHTML = "<option value=\"\"  disabled selected hidden>Спортиваная секция</option>";
            if (allSportSectionDB === []) {
                alert("Нет спортсекций");
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
                allCouchForSportSection.innerHTML = "<option value=\"\"  disabled selected hidden>Тренер</option>";
                if (allCouchForSportSectionDB === []) {
                    alert("Нету тренеров");
                }
                allCouchForSportSectionDB.forEach(item => {
                    const option = document.createElement('option');
                    option.value = item.id;
                    option.textContent = item.name;
                    if (item.name === couchName) {
                        option.selected = true;
                    }
                    allCouchForSportSection.appendChild(option);
                })
            })


    }

    // ЗАПИСЬ НА ТРЕНЕРОВКУ
    addActivity.addEventListener('click', () => {
        openRecordScreenWithData();
    })

    // Добавление инвентаря для пользователя

    addEquipment.addEventListener('click', () => {
        showScreen(recordForEquipment)

        const url = "/api/user/"
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Не авторизован")
                }
                return response.json();
            })
            .then(data => {
                const coachForInventoryDB = data.selectedCouches;
                coachForInventory.innerHTML = "<option value=\"couch\"  disabled selected hidden>Тренер</option>";
                if (coachForInventoryDB === []) {
                    alert("notCouches");
                }
                coachForInventoryDB.forEach(item => {
                    const option = document.createElement('option');
                    option.value = item.id;
                    option.textContent = item.name;
                    coachForInventory.appendChild(option);
                })

            });
    });


    document.getElementById("coachForInventory").addEventListener("change", function () {
        fetch("/api/inventory/couch/" + this.value, {
            method: "GET",
            headers: {'Content-type': 'application/json'},
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Неверная организация");
                }
                return response.json();
            })
            .then(data => displayEquipment(data))
            .catch(error => console.error(error));
    });

    function displayEquipment(equipmentList) {
        const container = document.getElementById("equipmentList");
        container.innerHTML = ''; // Clear previous content

        if (equipmentList.length === 0) {
            container.innerHTML = '<p>No equipment available.</p>';
            return;
        }

        equipmentList.forEach(equipment => {
            const equipmentDiv = document.createElement("div");
            equipmentDiv.classList.add("equipment-item");

            const name = document.createElement("h3");
            name.textContent = equipment.name;
            equipmentDiv.appendChild(name);

            const type = document.createElement("p");
            type.textContent = `Type: ${equipment.type}`;
            equipmentDiv.appendChild(type);

            const size = document.createElement("p");
            size.textContent = `Size: ${equipment.size}`;
            equipmentDiv.appendChild(size);

            const price = document.createElement("p");
            price.textContent = `Price: ${equipment.price} RUB`;
            equipmentDiv.appendChild(price);

            const selectButton = document.createElement("button");
            selectButton.textContent = "Выбрать";
            selectButton.addEventListener("click", () => selectEquipment(equipment.id, equipment.price, equipment.name));
            equipmentDiv.appendChild(selectButton);

            container.appendChild(equipmentDiv);
        });

        document.getElementById("getAllEquipment").style.display = "block";
    }

    function selectEquipment(id, price, name) {
        const couch_id = coachForInventory.value;

        fetch("api/balance/transfer/user/couch/" + couch_id, {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify({sum: price})
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        alert(text);
                        throw new Error(text);
                    });
                }
                return response.json();

            }).then(data => {
            console.log("Selected equipment ID:", id);
            const url = "api/inventory/user/" + id
            fetch(url, {
                method: "POST",
                headers: {"Content-type": "application/json"}
            }).then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        alert(text);
                        throw new Error(text);
                    })
                }
                alert("Было куплено " + name + " сумма перевода " + price);

                return response.json();
            })
        })
    }


    //Обновление данных у пользователя

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

    //ТАЙМЕР

    document.getElementById('stopwatch').addEventListener('click', () => {
        showScreen(stopwatchScreen);
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

    //....
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

// Замена тренера
function change() {
    window.location.href = "/account/user/changeCoach";

}

// Выход
function exit() {

    fetch("/api/login/user/exit")
        .then(response => {
            if (response.ok) {
                window.location.href = "/";
            }
        }).catch(error => console.error('Error:', error))
}


document.getElementById("month").addEventListener('change', function () {
    generateCalendar(this.value);

})

function generateCalendar(month) {

    const calendar = document.getElementById('calendar');
    calendar.innerHTML = '';
    let weekDays = ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"];
    for (let i = 0; i < 7; i++) {
        const emptySlot = document.createElement('div');
        emptySlot.textContent = weekDays[i];
        calendar.appendChild(emptySlot);
    }
    const date = new Date();
    date.setMonth(month);
    date.setDate(1);
    let firstDay = date.getDay() - 1;
    if (firstDay < 0) {
        firstDay = 6;
    }

    const year = date.getFullYear();
    const currentMonth = date.getMonth();
    const firstDayOfNextMonth = new Date(year, currentMonth + 1, 1);
    const daysInMonth = new Date(firstDayOfNextMonth - 1).getDate();

    // let occupiedDays = [];
    // let days;
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
        dayButton.id = 'button_' + day;
        dayButton.name = 'dayButton';
        dayButton.textContent = day;
        days = dayButton.id;


        // if (occupiedDays.includes(day)) {
        //     dayButton.style.backgroundColor = "red"; // Подкрашиваем занятый день в красный
        // }


        dayButton.addEventListener('click', function () {
            // Снимаем выделение с предыдущего выбранного дня
            const previouslySelected = document.querySelector('.calendar button.selected');
            if (previouslySelected) {
                previouslySelected.classList.remove('selected');
            }

            // // Проверяем, занят ли выбранный день
            // if (occupiedDays.includes(day)) {
            //     alert("Этот день занят!"); // Или подкрашиваем и выводим сообщение
            //     dayButton.style.backgroundColor = "red"; // Можно также визуально выделить
            // } else {
            //     dayButton.style.backgroundColor = ""; // Сброс цвета для свободных дней
            // }
            // Выделяем выбранный день
            dayButton.classList.add('selected');

        });
        calendar.appendChild(dayButton);

    }

}

let buttonEvents = {};

function getSchedule() {
    const coach = document.getElementById("coach").value
    const sportSection = document.getElementById("sports-section").value
    const month = parseInt(document.getElementById('month').value)

    const infoBoxes = document.querySelectorAll("div.info-box");
    infoBoxes.forEach(box => box.remove());


    let type = "";
    const radios = document.getElementsByName("training_type");

    for (const radio of radios) {
        if (radio.checked) {
            type = radio.value;
            break;
        }
    }
    const dayButtons = document.getElementsByName("dayButton");

    for (const dayButton of dayButtons) {
        dayButton.style.backgroundColor = "";
        if (buttonEvents.hasOwnProperty(dayButton.id)) {
            dayButton.removeEventListener('click', buttonEvents[dayButton.id]);
        }
    }

    const bookingTime = document.getElementById('allTime');
    bookingTime.classList.add('hidden');

    buttonEvents = {}

    if (coach !== "" && sportSection !== "" && !isNaN(month) && type !== "") {

        fetch("/api/schedule/couch/sport-section/" + coach + "/" + sportSection)
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
            console.log(data);
            const countTrainingsDay = {};
            data.forEach(scheduleDay => {
                let scheduleDate = new Date(scheduleDay.date);
                if (scheduleDate.getMonth() === month) {
                    const day = scheduleDate.getDate();
                    console.log(day);
                    if (type === scheduleDay.typeWorkout) {
                        if (!countTrainingsDay.hasOwnProperty(day)) {
                            countTrainingsDay[day] = 1
                        } else {
                            countTrainingsDay[day] += 1
                        }
                    }
                }
            })
            console.log(countTrainingsDay);
            data.forEach(scheduleDay => {
                let scheduleDate = new Date(scheduleDay.date);
                if (scheduleDate.getMonth() === month) {
                    const day = scheduleDate.getDate();
                    console.log(day)
                    const dayButton = calendar.querySelector(`#button_${day}`)
                    if (dayButton && type === scheduleDay.typeWorkout) {
                        dayButton.style.backgroundColor = "#00FFCC";
                        const listener = function () {
                            showDetailsBooking(scheduleDay, dayButton);

                        }
                        const showTime = function () {
                            showDetailsBookingTime(dayButton);
                        }
                        if (countTrainingsDay[day] === 1) {
                            dayButton.addEventListener('click', listener);
                            buttonEvents[dayButton.id] = listener;
                        } else if (countTrainingsDay[day] > 1) {
                            dayButton.addEventListener('click', showTime);
                            buttonEvents[dayButton.id] = showTime;
                            countTrainingsDay[day] = 0;
                        }
                    }

                }
            })
        })
    }
}

function showDetailsBookingTime(dayButton) {
    const coach = document.getElementById("coach").value
    const sportSection = document.getElementById("sports-section").value
    const month = parseInt(document.getElementById('month').value)
    const bookingTime = document.getElementById('allTime');
    const timeSlots = document.getElementById("times-record");
    timeSlots.innerHTML = '';


    let type = "";
    const radios = document.getElementsByName("training_type");

    for (const radio of radios) {
        if (radio.checked) {
            type = radio.value;
            break;
        }
    }

    fetch("/api/schedule/couch/sport-section/" + coach + "/" + sportSection)
        .then(response => {
            if (!response.ok) {
                throw new Error(response.message);
            }
            return response.json();
        }).then(data => {
        data.forEach(scheduleDay => {
            let scheduleDate = new Date(scheduleDay.date);
            if (scheduleDate.getMonth() === month) {
                let day = scheduleDate.getDate();
                if (parseInt(dayButton.textContent) === day && type === scheduleDay.typeWorkout) {

                    const time = new Date(scheduleDay.date).toLocaleTimeString(this.time);
                    const timeButton = document.createElement('button');
                    timeButton.textContent = time;
                    const listener = function () {
                        showDetailsBooking(scheduleDay, timeButton);
                    }
                    timeButton.addEventListener("click",listener);
                    timeSlots.appendChild(timeButton);
                }
            }
        })
    });
    bookingTime.classList.remove('hidden');

}

function showDetailsBooking(scheduleDay, dayButton) {
    // Создаем или показываем окно с информацией
    const infoBoxes = document.querySelectorAll("div.info-box");
    infoBoxes.forEach(box => box.remove());

    let infoBox = document.createElement('div');
    infoBox.classList.add('info-box');
    infoBox.display = 'flex';


    const time = new Date(scheduleDay.date).toLocaleTimeString(this.time);
    const description = scheduleDay.description || "Комментарий отсутствует";
    const place = scheduleDay.place || "Место не указано";
    const scheduleId = scheduleDay.id;

    infoBox.innerHTML = `
        <p><strong>Время:</strong> ${time}</p>
        <p><strong>Место:</strong> ${place}</p>
        <p><strong>Комментарий:</strong> ${description}</p>
        <button id="bookButton" style="width: 95%">Забронировать</button>
        <button id="closeInfoBox" style="width: 95%">Закрыть</button>
    `;


    document.body.appendChild(infoBox);

    // Позиционируем окно рядом с кнопкой дня
    const rect = dayButton.getBoundingClientRect();
    infoBox.style.top = `${rect.bottom + window.scrollY}px`;
    infoBox.style.left = `${rect.left + window.scrollX}px`;

    // Обработчик на кнопку "Забронировать"
    document.getElementById('bookButton').addEventListener('click', function () {
        bookTraining(scheduleId); // Функция бронирования
    });

    // Обработчик на кнопку "Закрыть"
    document.getElementById('closeInfoBox').addEventListener('click', function () {
        document.body.removeChild(infoBox); // Удаляем окно
    });
}


function bookTraining(scheduleId) {
    fetch("/api/booking/", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({schedule_id: scheduleId})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка при бронировании");
            }
            return response.json();
        })
        .then(data => {
            alert("Бронь успешно добавлена!");
        })
        .catch(error => {
            console.error("Ошибка при бронировании:", error);
        });
}

