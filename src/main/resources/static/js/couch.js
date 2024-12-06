document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('my-container')

    const profileTraining = document.getElementById('profile-training');
    const trainingScreen = document.getElementById('trainingScreen');

    const profileInventory = document.getElementById('profile-inventory');
    const inventoryScreen = document.getElementById('inventoryScreen');

    // const infoScreen = document.getElementById('info-screen');
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
        // container.classList.add('hidden');
        // infoScreen.classList.add('hidden');
        screen.classList.remove('hidden');
    };


    // Добавление расписания (Общие, индивидуальные)Создание кнопок добавления и удаление времени

    // Обработчик для кнопок выбора дня недели
    document.querySelectorAll('.day-button').forEach(button => {
        button.addEventListener('click', function (){
            const day = this.getAttribute('data-day');
            console.log(this.classList.contains('active-button'));
            if (this.classList.contains('active-button')) {
                this.classList.remove('active-button');
                this.classList.add('inactive-button');
            } else {
                this.classList.remove('inactive-button');
                this.classList.add('active-button');
            }
            console.log(this.classList.contains('active-button'));
        });
    });

    // Добавление времени
    document.getElementById('nextTimes').addEventListener('click', function () {
        const addTime = document.getElementById("allTimes");
        const selectedDays = [];
        const allWeekDays = document.querySelectorAll('.day-button');
        allWeekDays.forEach(day => {
            if (day.classList.contains("active-button")) {
                selectedDays.push(parseInt(day.getAttribute("data-day")))
            }
        })
        // Проверяем, выбраны ли дни недели
        if (selectedDays.length === 0) {
            alert('Выберите хотя бы один день недели.');
            return;
        }

        // Создаем контейнер для input и крестика
        const timeContainer = document.createElement("div");
        timeContainer.setAttribute("class", "time-container");
        timeContainer.style.display = "flex";
        timeContainer.style.alignItems = "center";
        timeContainer.style.marginBottom = "10px";

        // Создаем input для времени
        const dateTimeInput = document.createElement("input");
        dateTimeInput.setAttribute("type", "time");
        dateTimeInput.setAttribute("name", "time");
        dateTimeInput.setAttribute("class", "form-control");
        dateTimeInput.setAttribute("required", true);
        dateTimeInput.style.marginRight = "10px"; // Отступ справа для крестика


        // Создаем кнопку крестика для удаления
        const deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button"); // Кнопка без отправки формы
        deleteButton.innerHTML = "&times;"; // Символ крестика
        deleteButton.style.backgroundColor = "transparent"; // Прозрачный фон
        deleteButton.style.border = "none"; // Убираем границу
        deleteButton.style.fontSize = "20px"; // Размер текста крестика
        deleteButton.style.cursor = "pointer"; // Изменение курсора при наведении

        // Добавляем обработчик для удаления input и крестика
        deleteButton.addEventListener("click", function () {
            addTime.removeChild(timeContainer);
        });

        // Добавляем input и крестик в контейнер
        timeContainer.appendChild(dateTimeInput);
        timeContainer.appendChild(deleteButton);

        // Добавляем контейнер в DOM
        addTime.appendChild(timeContainer);
    });

    document.getElementById("training_type").addEventListener("change", function () {
        const type = document.getElementById("training_type").value;
        const general = document.getElementById("addDaysForGeneral");
        const individual = document.getElementById("addDaysForIndividual");

        if (type === "individual") {
            general.style.display = "none";
            individual.style.display = "block";
        } else {
            individual.style.display = "none";
            general.style.display = "block";
        }
    })

    const general = document.getElementById("addDaysForGeneral");
    const individual = document.getElementById("addDaysForIndividual");
    general.style.display = "none";
    individual.style.display = "block";




    function popupShow(text){
        const popup = document.getElementById("popup");
        popup.innerHTML = text;
        // Показываем всплывающее окно
        popup.classList.add("show");

        // Скрываем его через 3 секунды
        setTimeout(() => {
            popup.classList.remove("show");
        }, 3000);
    };


    //Добавить тренировку
    document.getElementById('addTraining').addEventListener('click', function () {
        fetch("/api/couch/sport-section/")
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
            data.forEach(sportSection => {
                const sportSectionSelect = document.getElementById("sportSections");
                const option = document.createElement("option");
                option.value = sportSection.id;
                option.textContent = sportSection.name;
                sportSectionSelect.appendChild(option);
            })
            // showScreen(trainingScreen);
        })

    });

    // Настроить время (начало с сегодняшнего дня)
    function todayDay() {
        let currentDateTime = new Date().toISOString();
        console.log(currentDateTime)
        return currentDateTime.split("Z")[0].slice(0, -7);
    }
    document.getElementById("date").setAttribute("min", todayDay())

    // добавляет блок времени +1
    document.getElementById('nextTime').addEventListener('click', function () {

        const addTime = document.getElementById("allTime");

        // Создаем контейнер для input и крестика
        const timeContainer = document.createElement("div");
        timeContainer.setAttribute("class", "time-container");
        timeContainer.style.display = "flex";
        timeContainer.style.alignItems = "center";

        // Создаем input для даты и времени
        const dateTimeInput = document.createElement("input");
        dateTimeInput.setAttribute("type", "datetime-local");
        dateTimeInput.setAttribute("name", "date");
        dateTimeInput.setAttribute("class", "form-control");
        dateTimeInput.setAttribute("required", true);
        dateTimeInput.style.marginRight = "10px"; // Отступ справа для крестика

        // Создаем кнопку крестика для удаления
        const deleteButton = document.createElement("button");
        deleteButton.setAttribute("type", "button"); // Кнопка без отправки формы
        deleteButton.innerHTML = "&times;"; // Символ крестика
        deleteButton.style.backgroundColor = "transparent"; // Прозрачный фон
        deleteButton.style.border = "none"; // Убираем границу
        deleteButton.style.fontSize = "20px"; // Размер текста крестика
        deleteButton.style.cursor = "pointer"; // Изменение курсора при наведении


        // Добавляем обработчик для удаления input и крестика
        deleteButton.addEventListener("click", function () {
            addTime.removeChild(timeContainer);
        });

        // Добавляем input и крестик в контейнер
        timeContainer.appendChild(dateTimeInput);
        timeContainer.appendChild(deleteButton);

        // Добавляем контейнер в DOM
        addTime.appendChild(timeContainer);
    });

    function newData(){
        const placeId = document.getElementById("place");
        const descriptionId = document.getElementById("description");
        const sumId = document.getElementById("sum");
        const startDateId = document.getElementById("startDate");
        const endDateId = document.getElementById("endDate");
        const dateId = document.getElementById("date");
        const nextTimesId = document.getElementById("nextTimes");

       placeId.value = "";
       descriptionId.value = "";
       sumId.value = "";
       startDateId.value = "";
       endDateId.value = "";
       dateId.value = "";
       nextTimesId.value = "";
    }

    // Добавление общих и индивидуальных тренировок
    profileTraining.addEventListener('submit', (e) => {
        e.preventDefault();

        const type = document.getElementById("training_type").value;
        const modal = new bootstrap.Modal(document.getElementById('trainingModal'));
        console.log(modal);

        // Добавить в общее расписание
        if (type === "general") {
            console.log(type);
            const currentSelectedDays = [];
            const allWeekDays = document.querySelectorAll('.day-button');
            allWeekDays.forEach(day => {
                if (day.classList.contains("active-button")) {
                    currentSelectedDays.push(parseInt(day.getAttribute("data-day")))
                }
            })
            const dayOfNow = new Date(document.getElementById("startDate").value);
            const dayOfEnd = new Date(document.getElementById("endDate").value);
            console.log(currentSelectedDays)

            for (let i = dayOfNow; i <= dayOfEnd; i.setDate(i.getDate() + 1)) {
                console.log(i.getDay())

                if (currentSelectedDays.includes(i.getDay())) {
                    const allTime = document.getElementsByName("time");
                    allTime.forEach(time => {
                        const [hours, minutes] = time.value.split(":") // ["11", "22"]
                        const formData = new FormData(profileTraining);
                        let date = new Date(i);
                        date.setHours(parseInt(hours));
                        date.setMinutes(parseInt(minutes))
                        const profileData = {
                            place: formData.get('place'),
                            description: formData.get('description'),
                            sum: formData.get('sum'),
                            typeWorkout: type,
                            date: date,

                        };
                        console.log(date)
                        const sportSectionSelect = document.getElementById("sportSections");
                        const sportSectionId = parseInt(sportSectionSelect.value);

                        fetch('/api/schedule/' + sportSectionId, {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(profileData)
                        })
                            .then(response => response.json())
                            .then(data => {
                                popupShow("Общая тренировка добавлена!")
                                newData();
                            })
                            .catch(error => console.error('Ошибка:', error));
                    })
                }
            }
        }
        //Добавление индивидуальных тренировок
        if (type === "individual") {

            console.log(type);
            const allDate = document.getElementsByName("date");
            allDate.forEach(date => {

                const formData = new FormData(profileTraining);
                const profileData = {
                    place: formData.get('place'),
                    description: formData.get('description'),
                    typeWorkout: type,
                    sum: formData.get('sum'),
                    date: date.value,

                };

                const sportSectionSelect = document.getElementById("sportSections");
                const sportSectionId = parseInt(sportSectionSelect.value);

                fetch('/api/schedule/' + sportSectionId, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(profileData)
                }).then(response => response.json())
                    .then(data => {
                        popupShow("Индивидуальная тренировка добавлена!")
                        newData();
                    })
                    .catch(error => console.error('Ошибка:', error));

            })
            // const modal = new bootstrap.Modal(document.getElementById('trainingModal'));
            // modal.hide();
        }
    });

    // Вывод расписание для тренера(Индивидуальные,Общие)

    document.getElementById('scheduleForCouch').addEventListener('shown.bs.modal', function () {
        const scheduleDateInput = document.getElementById('scheduleDate');

        // При изменении даты, загружаем расписание
        scheduleDateInput.addEventListener('change', function () {
            const selectedDate = scheduleDateInput.value;
            if (selectedDate) {
                loadCouchSchedule(selectedDate);
            }
        });
    });

    function loadCouchSchedule(selectedDate) {
        const couchesTrainingDiv = document.getElementById('couchesTraining');
        couchesTrainingDiv.innerHTML = '';

        // Загрузка расписания тренера
        fetch('/api/schedule/couch/')
            .then(response => response.json())
            .then(scheduleData => {
                // Фильтруем тренировки по выбранной дате
                const filteredSchedule = scheduleData.filter(schedule =>
                    new Date(schedule.date).toISOString().split('T')[0] === selectedDate);

                // Загрузка данных о бронированиях
                fetch('/api/booking/couchBooking/')
                    .then(response => response.json())
                    .then(bookingData => {
                        // Отображаем расписание с бронированиями
                        displayScheduleWithBookings(filteredSchedule, bookingData);
                    })
                    .catch(error => {
                        console.error('Ошибка загрузки бронирований:', error);
                        couchesTrainingDiv.innerHTML = 'Ошибка загрузки данных о бронированиях.';
                    });
            })
            .catch(error => {
                console.error('Ошибка загрузки расписания:', error);
                couchesTrainingDiv.innerHTML = 'Ошибка загрузки расписания.';
            });
    }

    function displayScheduleWithBookings(scheduleData, bookingData) {
        const couchesTrainingDiv = document.getElementById('couchesTraining');

        if (scheduleData.length === 0) {
            couchesTrainingDiv.innerHTML = '<p>Тренировок на эту дату нет.</p>';
            return;
        }

        // Индивидуальные тренировки
        const individualTrainings = scheduleData.filter(item => item.typeWorkout === 'individual');
        if (individualTrainings.length > 0) {
            const individualBlock = document.createElement('div');
            individualBlock.innerHTML = '<h5>Индивидуальные тренировки:</h5>';
            individualTrainings.forEach(training => {
                const trainingElement = createTrainingElement(training, bookingData);
                individualBlock.appendChild(trainingElement);

            });
            couchesTrainingDiv.appendChild(individualBlock);
        } else {
            couchesTrainingDiv.innerHTML += '<p>Нет индивидуальных тренировок на эту дату.</p>';
        }

        // Общие тренировки
        const generalTrainings = scheduleData.filter(item => item.typeWorkout === 'general');
        if (generalTrainings.length > 0) {
            const generalBlock = document.createElement('div');
            generalBlock.innerHTML = '<h5>Общие тренировки:</h5>';
            generalTrainings.forEach(training => {
                const trainingElement = createTrainingElement(training, bookingData);
                generalBlock.appendChild(trainingElement);
            });
            couchesTrainingDiv.appendChild(generalBlock);
        } else {
            couchesTrainingDiv.innerHTML += '<p>Нет общих тренировок на эту дату.</p>';
        }
    }

    function createTrainingElement(training, bookingData) {
        const trainingElement = document.createElement('div');
        const time = new Date(training.date).toLocaleTimeString();
        let buttonCancel = document.createElement('button');
        buttonCancel.textContent = "Удалить";
        buttonCancel.style.backgroundColor = "aqua";
        buttonCancel.style.fontSize = "15px";
        buttonCancel.style.cursor = "pointer"

        const bookingsForTraining = bookingData.filter(booking => booking.schedule_id === training.id);

        buttonCancel.onclick = () => {
            bookingsForTraining.forEach(booking => {

                fetch("/api/booking/" + booking.id, {
                    method: 'DELETE',
                    headers: {'Content-Type': 'application/json'}
                }).then(response => {
                    if (!response.ok) {
                        throw new Error(response.message);
                    }
                    return response.json()
                }).then(
                    fetch("/api/schedule/" + training.id, {
                        method: 'DELETE',
                        headers: {'Content-Type': 'application/json'}
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(response.message);
                            }
                            return response.json();
                        })
                )

            })

        }
        let bookedUsers = bookingsForTraining.map(booking => booking.user.name + " " + booking.user.surname).join(', ');

        let divElements = document.createElement("div");
        divElements.className = "card-body";
        divElements.innerHTML = `
                <p><strong>Время:</strong> ${time}</p>
                <p><strong>Место:</strong> ${training.place || 'Место не указано'}</p>
                <p><strong>Описание:</strong> ${training.description || 'Без описания'}</p>
                <p><strong>Забронировано:</strong> ${bookedUsers || 'Нет бронирований'}</p>
                `;

        divElements.appendChild(buttonCancel);
        trainingElement.className = "card mb-3";
        trainingElement.appendChild(divElements);

        return trainingElement;
    }


    profileInventory.addEventListener('submit', (ev) => {
        ev.preventDefault();
        const formData = new FormData(profileInventory);
        const profileDate = {
            name: formData.get('name'),
            type: formData.get('type'),
            size: formData.get('size'),
            price: formData.get('price'),
        }

        fetch('/api/inventory/couch/', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(profileDate)
        }).then(response => response.json())
            .then(data => alert('Инвентарь добавлен: ' + JSON.stringify(data))            )
            .catch(error => console.error('Ошибка:', error));

    });

    // Инвентарь

    document.getElementById("inventory").addEventListener("click", function () {
        const inventoryModalScreen = new bootstrap.Modal(document.getElementById("inventoryModalScreen"));
        inventoryModalScreen.show();

        // Элемент, куда будет добавляться информация
        const equipmentInfo = document.getElementById('equipment-info');
        const equipmentInfoSpan = equipmentInfo.querySelector('span');

        // Функция обработки данных инвентаря
        const processInventoryData = (data) => {
            console.log("Обработка инвентаря...");
            let infoBox2 = document.createElement('div');
            infoBox2.classList.add('info-box2');
            infoBox2.style.display = 'flex';
            infoBox2.style.flexWrap = 'wrap';

            data.forEach(profile => {
                console.log("Обработка профиля:", profile);

                const profileDiv = document.createElement("div");
                profileDiv.classList.add("profile-item");
                profileDiv.style.border = "1px solid #ccc"; // Добавляем немного стилей
                profileDiv.style.margin = "10px";
                profileDiv.style.padding = "10px";

                // Добавляем данные профиля
                const name = document.createElement("p");
                name.textContent = `Наименования: ${profile.name || "Не указано"}`;
                profileDiv.appendChild(name);

                const price = document.createElement("p");
                price.textContent = `Цена: ${profile.price || 0}`;
                profileDiv.appendChild(price);

                const type = document.createElement("p");
                type.textContent = `Тип: ${profile.type || "Не указано"}`;
                profileDiv.appendChild(type);

                const size = document.createElement("p");
                size.textContent = `Размер: ${profile.size || "Не указано"}`;
                profileDiv.appendChild(size);

                // Кнопка "Удалить"
                const deleteButton = document.createElement("button");
                deleteButton.classList.add("delete-button");
                deleteButton.textContent = "Удалить";
                deleteButton.style.backgroundColor = "aqua";
                deleteButton.style.fontSize = "15px";
                deleteButton.style.cursor = "pointer";
                deleteButton.style.borderRadius = "2px";

                profileDiv.appendChild(deleteButton);

                // Добавляем обработчик для удаления
                deleteButton.addEventListener('click', function () {
                    deleteInventory(profile.id, profileDiv);
                });

                // Добавляем профиль в infoBox
                infoBox2.appendChild(profileDiv);
            });

            return infoBox2;
        };

        // Функция для загрузки данных с сервера
        const fetchData = () => {
            // Показываем, что данные загружаются
            equipmentInfoSpan.textContent = "Загрузка...";

            fetch('/api/inventory/couchInventory/')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Ошибка HTTP: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Инвентарь загружен:", data);

                    // Отображаем общее количество
                    equipmentInfoSpan.textContent = data.length;

                    // Удаляем старое содержимое и добавляем новое
                    const inventoryResult = processInventoryData(data);
                    if (equipmentInfo.nextElementSibling) {
                        equipmentInfo.nextElementSibling.remove();
                    }
                    equipmentInfo.after(inventoryResult);
                })
                .catch(error => {
                    console.error("Ошибка загрузки данных:", error);
                    equipmentInfoSpan.textContent = `Ошибка: ${error.message}`;
                });
        };

        fetchData();
    });

// Функция для удаления элемента инвентаря
    function deleteInventory(id, profileDiv) {
        fetch(`/api/inventory/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    console.log(`Удален инвентарь с ID: ${id}`);
                    profileDiv.remove(); // Удаляем элемент из DOM
                } else {
                    return response.json().then(err => {
                        throw new Error(err.message || "Ошибка при удалении");
                    });
                }
            })
            .catch(error => {
                console.error("Ошибка удаления:", error);
                alert(`Не удалось удалить элемент: ${error.message}`);
            });
    }

    //Статистика

    document.getElementById("statistics").addEventListener("click", function () {
        const infoScreenModal = new bootstrap.Modal(document.getElementById("inventoryScreenModal"));
        infoScreenModal.show();

        // Функция обработки данных профиля
        const processProfileData = (data) => {
            let infoBox3 = document.createElement('div');
            infoBox3.classList.add('info-box3');
            infoBox3.display = 'flex';


            const name = data.name || "Имя отсутствует";
            const balance = data.balance || "Баланс 0";
            const experience = data.experience || "Стаж не указано"
            const login = data.login?.login || "Login не найден";

            infoBox3.innerHTML = `
            <h4>Профиль:</h4>
            <p><strong>Имя:</strong> ${name}</p>
            <p><strong>Баланс:</strong> ${balance}</p>
            <p><strong>Стаж:</strong> ${experience}</p>
            <p><strong>Логин:</strong> ${login}</p>`;

            return infoBox3;


            // console.log("profile")
            // const infoBox = document.createElement('div');
            // infoBox.classList.add('info-box');
            // infoBox.style.display = 'flex';
            // infoBox.style.flexDirection = 'column';
            //
            // if (!data || Object.keys(data).length === 0) {
            //     infoBox.innerHTML = '<p>Нет данных профиля</p>';
            //     return infoBox;
            // }
            //
            // const profileDiv = document.createElement("div");
            // profileDiv.classList.add("profile-item");
            //
            // // Добавляем данные профиля
            // const name = document.createElement("p");
            // name.textContent = `Имя: ${data.name || "Не указано"}`;
            // profileDiv.appendChild(name);
            //
            // const balance = document.createElement("p");
            // balance.textContent = `Баланс: ${data.balance || 0}`;
            // profileDiv.appendChild(balance);
            //
            // const experience = document.createElement("p");
            // experience.textContent = `Опыт: ${data.experience || "Не указано"}`;
            // profileDiv.appendChild(experience);
            //
            // const login = document.createElement("p");
            // login.textContent = `Логин: ${data.login?.login || "Не указано"}`;
            // profileDiv.appendChild(login);
            //
            // infoBox.appendChild(profileDiv);
            //
            // return infoBox;
        };

        // Функция обработки данных расписания
        const processBookingData = (data) => {
            console.log("booking")
            let infoBox3 = document.createElement('div');
            infoBox3.classList.add('info-box3');
            infoBox3.display = 'flex';


            const place = data.schedule?.place || "Место положения отсутствует";
            const description = data.description || "Коментарий нету";
            const date = data.date || "Время не указано"
            const typeWorkout = data.typeWorkout || "Тип тренировки не указан";
            const price = data.sum || "Цена не указана";


            infoBox3.innerHTML = `
            <h4>Расписание тренировок:</h4>
            <p><strong>Место проведения:</strong> ${place}</p>
            <p><strong>Комментарий:</strong> ${description}</p>
            <p><strong>Дата:</strong> ${date}</p>
            <p><strong>Тип тренировки:</strong> ${typeWorkout}</p>
            <p><strong>Цена:</strong> ${price}</p>`;

            return infoBox3;

        };

        // Обратобка
        const fetchData = () => {

            // Обработка расписания
            fetch('/api/booking/couchBooking/')
                .then(response => response.json())
                .then(data => {
                    console.log("BOOKING")
                    const bookingResult = processBookingData(data);
                    trainerInfo.innerHTML = bookingResult.innerHTML;
                })
                .catch(error => {
                    trainerInfo.innerHTML = 'Ошибка загрузки данных расписания: ' + error;
                });

            // Обработка профиля
            fetch('/api/couch/getCouch/')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("PROFILE")
                    const profileResult = processProfileData(data);
                    // Очищаем контейнер перед вставкой
                    profileInfo.innerHTML = '';
                    profileInfo.appendChild(profileResult);
                })
                .catch(error => {
                    profileInfo.innerHTML = `<p>Ошибка загрузки данных профиля: ${error.message}</p>`;
                });
        };

        // Обновление данных при нажатии кнопки
        refreshButton.addEventListener('click', fetchData);

        // Первоначальная загрузка
        fetchData();


    });

    // document.getElementById('back-to-main-inventory').addEventListener('click', () => {
    //     window.location.display.style.label = "close";
    // });
    // document.getElementById('back-to-main-timer').addEventListener('click', () => {
    //     window.location.display.style.label = "none";
    //
    // });


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


    document.getElementById('addExercises').addEventListener('click', function () {
        let exerciseInputs = '';
        for (let i = 1; i <= 5; i++) {
            exerciseInputs += `
            <label for="exercise${i}" class="form-label">Упражнение ${i}</label>
            <input type="text" class="form-control mb-3" id="exercise${i}" name="exercise${i}" placeholder="Введите название упражнения">
        `;
        }
        document.getElementById('exerciseInputs').innerHTML = exerciseInputs;
    });

// Добавление упражнений
    document.getElementById('exerciseForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Останавливаем стандартное поведение формы

        const exercises = [];
        for (let i = 1; i <= 5; i++) {
            exercises.push(document.getElementById('exercise' + i).value);
        }

        fetch('/api/schedule/addExercise/', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ exercises: exercises })
        })
            .then(response => response.json())
            .then(data => {
                alert('Упражнения добавлены: ' + JSON.stringify(data));
                const modal = bootstrap.Modal.getInstance(document.getElementById('exerciseModal'));
                modal.hide();
            })
            .catch(error => console.error('Ошибка:', error));
    });


// Таймер


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


