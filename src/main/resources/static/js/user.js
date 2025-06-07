document.addEventListener('DOMContentLoaded', () => {
    const mainScreen = document.getElementById('main-screen');
    const profileScreen = document.getElementById('profile-screen');
    const infoScreen = document.getElementById('info-screen');
    const notesScreen = document.getElementById('notes-screen');
    const recordForSection = document.getElementById('recordForSection');
    const recordForEquipment = document.getElementById('recordForEquipment');
    const chooseTrainer = document.getElementById('choose-trainer');
    const changeToCoach = document.getElementById('changeToCoach');
    const addBalance = document.getElementById("add-balance");


    const profileForm = document.getElementById('profile-form');
    const profileInfo = document.getElementById('profile-info');
    const trainerInfo = document.getElementById('trainer-info');
    const trainerRest = document.getElementById('trainer-rest');
    const equipmentInfo = document.getElementById('equipment-info');
    const modal = document.getElementById("balance-modal");
    let confirmButton = document.getElementById("confirm-add-balance");


    const addEquipment = document.getElementById('add-equipment');
    const addActivity = document.getElementById('add-activity');


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


    const showScreen = (screen) => {
        modal.classList.add('hidden')
        mainScreen.classList.add('hidden');
        profileScreen.classList.add('hidden');
        infoScreen.classList.add('hidden');
        notesScreen.classList.add('hidden');
        recordForSection.classList.add('hidden');
        recordForEquipment.classList.add('hidden');
        changeToCoach.classList.add('hidden');
        screen.classList.remove('hidden');
    };

    const params = new URLSearchParams(window.location.search);
    const sports = params.get("sport");



    fetch("/api/sport-section/"+sports)
        .then(response => {
            if (!response.ok) {
                throw new Error("Неверная организация")
            }
            return response.json();
        }).then(data =>{
        let sportSection ='';
        sportSection = data.name;
        console.log(sportSection);

        if (sports) {
            const body = document.body;
            switch (sportSection) {
                case "Фитнес":
                    body.style.backgroundImage = "url('../img/фитнес4.jpg')";
                    break;
                case "Бокс":
                    body.style.backgroundImage = "url('../img/boks-sila-udar3.jpg')";
                    break;
                // Добавьте остальные ID
                default:
                    body.style.backgroundImage = "url('../img/Общий фон.jpg')";
            }

        }
    });



    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const urlParamsCouchId = Number(urlParams.get('couchId'));
    const urlParamSectionId = Number(urlParams.get("section"));

    console.log(urlParamSectionId);
    if (urlParams.get("page") === 'recordScreen') {
        //TODO: fetch запрос к api user Couch (проверить на наличий этого (urlParams.get('couch')) тренера по id)если такого тренера нету открыавем openRecordScreenWithData() поверх него function popup и создаем окно urlParams.get('couch') о таком тренере с двумя кнопками добавить и закрыть) если добавить то вызывается запрос который добавляет этого тренера к user и открываем (openRecordScreenWithData(couchId))
        // заменить в параметре urlParams.get('couch') имя на id;

            fetch("/api/user/couch/", {
                method: "GET",
                headers: { 'Content-Type': 'application/json' },

            }).then(response => {
                if (!response.ok) {
                    throw new Error("Ошибка получения тренеров");
                }
                return response.json();
            }).then(data => {
                console.log("Список тренеров пользователя:", data);
                const foundCouch = data.find(couchInfo => couchInfo.id === urlParamsCouchId);
                console.log("Найденный тренер:", foundCouch);

                if (foundCouch) {
                    openRecordScreenWithData(urlParamsCouchId, urlParamSectionId);
                } else {
                    fetch(`/api/couch/${urlParamsCouchId}`)
                        .then(response => {
                            if (!response.ok) throw new Error("Тренер не найден");
                            return response.json();
                        })
                        .then(couch => {
                            openRecordScreenWithData();
                            showAddCouchPopup(couch);
                        })
                        .catch(error => {
                            console.error("Ошибка при получении данных тренера:", error);
                            alert("Не удалось загрузить информацию о тренере.");
                        });
                }
            }).catch(err => {
                console.error("Ошибка при получении тренеров пользователя:", err);
            });

    }

    if (urlParams.get("page") === 'scheduleScreen') {
        openRecordScreenWithData();
    }
    if (urlParams.get("page") === 'addBalanceScreen') {
        showScreen(modal);
    }
    if (urlParams.get("page") === 'allInfo') {
        showScreen(infoScreen);
    }


    function popup(text) {
        const popup = document.getElementById("popup");
        popup.innerHTML = text;
        popup.classList.add("show");

        setTimeout(() => {
            popup.classList.remove("show")
        }, 3000)

    }


    function showAddCouchPopup(couch){
        const popup = document.createElement('div');
        popup.classList.add('popup-overlay');


        const getCoach = document.createElement("div");
        getCoach.classList.add("coach-container");

        // Создаем контейнер для фотографии и информации
        const coachInfo = document.createElement("div");
        coachInfo.classList.add("coach-info");

        // Фотография тренера
        const photo = document.createElement("img");
        photo.src = couch.photo || '/coach/photo/default.jpg';
        photo.alt = `${couch.name}`;
        photo.classList.add("coach-photo");
        coachInfo.appendChild(photo);

        // Контейнер для текста (имя, стаж, виды спорта)
        const textInfo = document.createElement("div");
        textInfo.classList.add("text-info");

        // Имя тренера
        const name = document.createElement("p");
        name.textContent = `${couch.name}`;
        textInfo.appendChild(name);

        // Стаж тренера
        const experience = document.createElement("p");
        experience.textContent = `Опыт: ${couch.experience || 'Неизвестно'}`;
        textInfo.appendChild(experience);

        // Добавляем информацию в coachInfo
        coachInfo.appendChild(textInfo);

        // Контейнер для кнопок
        const buttonsContainer = document.createElement("div");
        buttonsContainer.classList.add("buttons-container");

        // Кнопка "Выбрать"
        const selectButton = document.createElement("button");
        selectButton.textContent = "Добавить";
        selectButton.classList.add("select-button");
        selectButton.onclick = () => {
            fetch(`/api/user/couch/${couch.id}`, {
                method: "POST",
                headers: { 'Content-Type': 'application/json' }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Ошибка при добавлении тренера");
                    }
                    return response.json();
                })
                .then(() => {
                    document.body.removeChild(popup);
                    openRecordScreenWithData(couch.id, urlParams.get('sport'));
                })
                .catch(err => {
                    console.error("Ошибка при добавлении тренера:", err);
                    alert("Не удалось добавить тренера.");
                });
        }

        const closeButton = document.createElement('button');
        closeButton.textContent = "Закрыть";
        closeButton.addEventListener("click", function (){
            document.body.removeChild(popup);
        })

        buttonsContainer.appendChild(selectButton);
        buttonsContainer.appendChild(closeButton);

        // Добавляем все элементы в главный контейнер getCoach
        getCoach.appendChild(coachInfo);
        getCoach.appendChild(buttonsContainer);

        // Добавляем getCoach в DOM
        popup.appendChild(getCoach);
        document.body.appendChild(popup);

    }




    //Пополнение баланса
    addBalance.onclick = function () {
        showScreen(modal);
    }

// Действие при нажатии на кнопку "Пополнить"
    confirmButton.onclick = function () {
        let amount = document.getElementById("amount").value;

        fetch("/yoomoney/getInvoicePay/" + amount, {
            method: "GET",
            headers: {'Content-type': 'application/json'}
        })
            .then(response => {
                console.log(response);
                if (!response.ok) {
                    throw new Error("Не получается оплатить")
                }
                return response.text()
                    .then(data => {
                        if (data.startsWith("https://")) {
                            window.location.href = data;
                        } else {
                            alert(data);
                        }
                    })
            })
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
                    window.location.href = 'account?page=recordScreen&couch=' + item.id
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
        const trainerRest = document.getElementById('trainer-rest').querySelector('span')
        const balanceInfo = document.getElementById('balance-info').querySelector('span')

        fetch('/api/booking/getAllBookingUser/')
            .then(response => response.json())
            .then(data => {
                trainerRest.textContent = data.length;
            })

        fetch('/api/user/')
            .then(response => response.json())
            .then(data => {
                profileInfo.textContent = `${data.name} ${data.surname}, ${data.height} cm, ${data.weight} kg`;
            });

        fetch("/api/balance/balanceUser/")
            .then(response => response.json())
            .then(data => {
                const balance = data !== null ? data : 0;
                balanceInfo.textContent = `${balance}`;

            });

        fetch('/api/user/couch/')
            .then(response => response.json())
            .then(data => {
                getUserCouch(data)
            });


        fetch('/api/inventory/userInventory/')
            .then(response => response.json())
            .then(data => {
                let count = 0;
                data.forEach(item => {
                    if (item !== null) {
                        count++;
                    }
                })
                equipmentInfo.innerHTML = '';
                let getUserInventory = getCountEquipment(count, data);
                equipmentInfo.appendChild(getUserInventory);
                document.getElementById('getAllEquipment').addEventListener("click", function () {
                    getEquipment()
                })

            });
    });


    function getUserCouch(data) {
        const container = document.getElementById('trainer-info');
        container.innerHTML = '';
        container.textContent = "Тренера: ";

        if (data === 0) {
            container.innerHTML = '<p>Нету тренеров</p>';
            return;
        }
        data.forEach(item => {
            const newContainer = document.createElement('div');
            newContainer.classList.add('trainer-container');

            const trainer = document.createElement('h3')
            trainer.textContent = 'Тренер: '
            newContainer.appendChild(trainer);

            const name = document.createElement("p")
            name.textContent = 'Имя ' + item.name;
            newContainer.appendChild(name);

            const experience = document.createElement("p")
            experience.textContent = 'Опыт ' + item.experience;
            newContainer.appendChild(experience);

            container.appendChild(newContainer);


        })
    }


    document.getElementById('equipment-info').addEventListener("click", function (event) {
        if (event.target && event.target.id === "getAllEquipment") {
            getEquipment();
        }
    });

    function getCountEquipment(count, data) {
        let infoBox = document.createElement("equipment-info");
        infoBox.innerHTML = '';
        infoBox.style.color = 'darkslategrey';


        let firstItemHtml;
        let firstItem = data.length > 0 ? data[0] : null;
        data.forEach(data => {
            firstItemHtml = firstItem ? `<p>${data.name} (${data.amount} шт.)</p>` : "<p>Нет доступного инвентаря</p>";
            infoBox.innerHTML += `${firstItemHtml}`;
        })


        infoBox.innerHTML += `
        <p><strong><button id="getAllEquipment" class="btn btn-primary">Подробнее⬇</button></strong></p>
        <div id="showAllEquipment" style="display: none" ></div>`;

        return infoBox;


    }

    const getEquipment = () => {
        fetch('/api/inventory/userInventory/')
            .then(response => response.json())
            .then(data => {

                const container = document.getElementById('showAllEquipment')
                container.innerHTML = '<h4>Список инвентаря:</h4>';
                container.style.display = "block";

                if (data.length === 0) {
                    container.innerHTML = '<p>No equipment available</p>';
                    return;
                }
                data.forEach(item => {
                    const newElement = document.createElement("div");
                    newElement.classList.add("equipment-item");

                    const inventory = document.createElement("h4");
                    inventory.textContent = 'Инвентарь: ';
                    newElement.appendChild(inventory);

                    const name = document.createElement("p");
                    name.textContent = `Наименование - ` + item.name;
                    newElement.appendChild(name);

                    const type = document.createElement("p");
                    type.textContent = `Тип - ` + item.type;
                    newElement.appendChild(type);

                    const price = document.createElement("p");
                    price.textContent = `Цена - ` + item.price;
                    newElement.appendChild(price);

                    const size = document.createElement("p");
                    size.textContent = `Размер - ` + item.size;
                    newElement.appendChild(size);

                    const amount = document.createElement("p");
                    amount.textContent = `Количество - ` + item.amount;
                    newElement.appendChild(amount);

                    container.appendChild(newElement);

                })
                container.innerHTML += '<button id="closeInventory" class="btn btn-primary">Закрыть список⬆</button>'
                document.getElementById("closeInventory").addEventListener("click", function () {
                    container.style.display = 'none';
                })
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

    function openRecordScreenWithData(couchId, couchSectionId) {
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
                if (item.id === couchSectionId) {
                    option.selected = true;
                }
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
                    if (item.id === couchId) {
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
        const container = document.getElementById("equipmentList");
        document.getElementById("getAllEquipments").style.display = 'none';
        container.innerHTML = '';
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
        document.getElementById("getAllEquipments").style.display = 'block';
        container.innerHTML = '';

        if (equipmentList.length === 0) {
            container.innerHTML = '<p>Нет в наличий инвентаря.</p>';
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

            const amount = document.createElement("p");
            amount.textContent = `Amount: ${equipment.amount} Шт`;
            equipmentDiv.appendChild(amount);


            const selectButton = document.createElement("button");
            selectButton.textContent = "Выбрать";
            if (equipment.amount > 0) {
                selectButton.addEventListener("click", () => selectEquipment(equipment.id, equipment.price, equipment.name));
            } else {
                selectButton.style.display = "none";
            }

            equipmentDiv.appendChild(selectButton);

            container.appendChild(equipmentDiv);

        });

        document.getElementById("getAllEquipments").style.display = "block";
    }

    function selectEquipment(id, price, name) {
        const couch_id = coachForInventory.value;
        const url = "api/inventory/user/" + id
        if (confirm("Подтверждаете покупку?")) {
            fetch(url, {
                method: "POST",
                headers: {"Content-type": "application/json"}
            }).then(async response => {
                if (response.status === 402) {
                    // Недостаточно средств
                    alert("Недостаточно средств для покупки");
                    throw new Error("Недостаточно средств для покупки");
                } else if (!response.ok) {
                    throw new Error("Ошибка при покупки");
                }
                return response.json();
            })
                .then(data => {
                    fetch("api/balance/transfer/user/couch/" + couch_id, {
                        method: "POST",
                        headers: {"Content-type": "application/json"},
                        body: JSON.stringify({sum: price})
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(response.message);
                            }
                            popup("Был куплен инвентарь: " + name);
                            setTimeout(() => {
                                window.location.href = 'account?';
                            }, 1000); // немного подождём, чтобы юзер успел увидеть popup

                            return response.json();
                        }).then(date => {
                    }).catch(error => {
                        console.error("Ошибка при переводе:", error);
                    })

                })
        }
    }

    //Обновление данных у пользователя

    profileScreen.addEventListener('submit', (e) => {
        e.preventDefault();
        newLoginMessage.style.display = 'none';
        const formData = new FormData(profileForm);
        const profileData = {
            name: formData.get('firstName'),
            surname: formData.get('lastName'),
            height: formData.get('height'),
            weight: formData.get('weight')
        };
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
    document.getElementById('notesButton').addEventListener('click', () => {
        showScreen(document.getElementById('notes-screen'));
    });


    //....
    document.getElementById('save-notes').addEventListener('click', () => {
        const notes = document.getElementById('notes').value;
        // Send notes to backend
        fetch('/api/user/addNotesForUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({notes: notes})
        })
            .then(response => response.json())
            .then(data => {
                alert('Заметки сохранены');
                document.getElementById('notes').value = '';
            });
    });

    document.getElementById('show-notes').addEventListener('click', () => {
        fetch('/api/user/getUserNotes')
            .then(response => response.json())
            .then(data => {
                const notesContainer = document.getElementById('all-notes');
                notesContainer.innerHTML = ''; // очищаем перед добавлением

                if (data.length === 0) {
                    notesContainer.innerHTML = '<p>Заметок пока нет.</p>';
                    return;
                }

                const sortedNotes = data.sort((a, b) => new Date(b.dateTime) - new Date(a.dateTime));
                const lastNotes = sortedNotes.slice(0, 3);
                const remainingNotes = sortedNotes.slice(3);

                // Отображаем последние 3
                lastNotes.forEach(note => {
                    const noteElement = document.createElement('div');
                    noteElement.className = 'note-card';
                    noteElement.innerHTML = `
                    <small>${new Date(note.localDateTime).toLocaleString()}</small>
                    <p style="white-space: pre-line">${note.text || note.notes}</p>
                `;
                    notesContainer.appendChild(noteElement);
                });

                // Если есть еще заметки, добавляем кнопку "Показать все"
                if (remainingNotes.length > 0) {
                    const showAllBtn = document.createElement('button');
                    showAllBtn.textContent = 'Показать все';
                    showAllBtn.id = 'show-all-btn';
                    showAllBtn.addEventListener('click', () => {
                        remainingNotes.forEach(note => {
                            const noteElement = document.createElement('div');
                            noteElement.className = 'note-card';
                            noteElement.innerHTML = `
                            <small>${new Date(note.dateTime).toLocaleString()}</small>
                            <p style="white-space: pre-line">${note.text || note.notes}</p>
                        `;
                            notesContainer.appendChild(noteElement);
                        });
                        showAllBtn.remove(); // убираем кнопку после показа всех
                    });
                    notesContainer.appendChild(showAllBtn);
                }

                document.getElementById('show-notes').style.display = 'none';
                document.getElementById('hide-notes').style.display = 'block';
            })
            .catch(err => {
                console.error('Ошибка при загрузке заметок:', err);
                alert('Ошибка при загрузке заметок');
            });
    });

    document.getElementById('hide-notes').addEventListener('click', () => {
        const notesContainer = document.getElementById('all-notes');
        notesContainer.innerHTML = '';
        document.getElementById('show-notes').style.display = 'block';
        document.getElementById('hide-notes').style.display = 'none';
    });


    // Back to main screen buttons
    document.getElementById('back-to-main-profile').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-info').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-notes').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-record').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    document.getElementById('back-to-main-coach').addEventListener('click', () => {
        showScreen(mainScreen);
    });
    document.getElementById('back-to-main-balance').addEventListener('click', () => {
        showScreen(mainScreen)
    });
    document.getElementById('back-to-main-equipment').addEventListener('click', () => {
        showScreen(mainScreen);
    });

    const currentMonth = new Date().getMonth();
    document.getElementById('month').value = currentMonth;
    generateCalendar(currentMonth);


    openTgBot();

    //openTgBotIdUser();
});
// function openTgBotIdUser(){
//     document.getElementById("notification").addEventListener("click",function (){
//         fetch("/getIdTg")
//             .then(response => {
//                 if(!response.ok){
//                     throw new Error(response.message);
//                 }
//                 return response.text();
//             }).then(data => {
//                 const tgId = data.id;
//         })
//     })
// }


function openTgBot() {
    document.getElementById("notification").addEventListener("click", function () {
        fetch("/api/login/hashId/")
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.text();
            }).then(data => {
            window.location.href = "https://t.me/sportliveapp_bot?start=auth_" + data;
        })
    })
}

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

function displayTariffs() {
    const tariffContainer = document.getElementById("tariffs-container");
    const coach = document.getElementById("coach").value
    const sportSection = document.getElementById("sports-section").value

    tariffContainer.innerHTML = "";

    fetch("/api/seasonTickets/ticket/get/" + sportSection + "/" + coach)
        .then(response => {
            if (!response.ok) {
                throw new Error("нету такого тарифа")
            }
            return response.json()
        })
        .then(data => {
            data.forEach(item => {
                const tariffs = document.createElement("div");
                tariffs.classList.add("tariffs");
                tariffs.display = 'flex';


                const name = item.name || "Не указано";
                const description = item.description || "Не указано";
                const days = item.days || "Не указано";
                const sum = item.sum || "Не указано";
                let schedule = "";
                item.date.forEach(date => {
                    schedule += `${date.dayOfWeek} в ${date.time}, `
                })
                const uuid = item.uuid;

                tariffs.innerHTML += `
            <p><strong>${name}</strong> </p>
            <p>${description}</p>
            <p><strong>Сумма:</strong> ${sum} руб, ${days} дней</p>
            <p><strong>Расписание:</strong> ${schedule.slice(0, -2)}</p>
            <button class="btn btn-success saveGeneral">Забронировать</button>`

                tariffs.querySelector('.saveGeneral').addEventListener('click', function () {
                    if (confirm("Подтверждаете бронирование?")) {
                        // метод где будет вызываться пост запрос и передавать туда item.uuid
                        fetch("/api/seasonTickets/book/" + uuid, {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                        })
                            .then(async response => {
                                getSchedule();
                                if (response.status === 402) {
                                    // Недостаточно средств
                                    alert("Недостаточно средств для бронирования!");
                                    throw new Error("Недостаточно средств для бронирования");

                                } else if (!response.ok) {
                                    throw new Error("Ошибка при бронировании");
                                }
                                alert("Бронь успешно добавлена!");
                                transactionForCouch(coach, sum);
                                return response.json();
                            })
                    }
                })

                tariffContainer.appendChild(tariffs);
            })

        })
}


let buttonEvents = {};
let userId;

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

    console.log(dayButtons);

    for (const dayButton of dayButtons) {
        dayButton.style.backgroundColor = "";
        if (buttonEvents.hasOwnProperty(dayButton.id)) {
            dayButton.removeEventListener('click', buttonEvents[dayButton.id]);
        }
    }

    const bookingTime = document.getElementById('allTime');
    bookingTime.classList.add('hidden');

    const tariffContainer = document.getElementById("tariffs-container");
    if (type === "general") {
        tariffContainer.classList.remove("hidden");
        displayTariffs();
    } else {
        tariffContainer.classList.add("hidden");
    }


    buttonEvents = {}

    if (coach !== "" && sportSection !== "" && !isNaN(month) && type !== "") {

        fetch("/api/schedule/couch/sport-section/" + coach + "/" + sportSection)

            .then(response => {
                if (!response.ok) {
                    throw new Error(response.message);
                }
                return response.json();
            }).then(data => {
            const countTrainingsDay = {};
            data.forEach(scheduleDay => {
                let scheduleDate = new Date(scheduleDay.date);
                if (scheduleDate.getMonth() === month) {
                    const day = scheduleDate.getDate();
                    if (type === scheduleDay.typeWorkout) {
                        if (!countTrainingsDay.hasOwnProperty(day)) {
                            countTrainingsDay[day] = 1
                        } else {
                            countTrainingsDay[day] += 1
                        }
                    }
                }
            })

            data.forEach(scheduleDay => {
                let scheduleDate = new Date(scheduleDay.date);
                if (scheduleDate.getMonth() === month) {
                    const day = scheduleDate.getDate();
                    const dayButton = calendar.querySelector(`#button_${day}`)
                    if (dayButton && type === scheduleDay.typeWorkout) {
                        dayButton.style.backgroundColor = "#00FFCC";

                        fetch("/api/user/")
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error(response.message);
                                }
                                return response.json();
                            }).then(user => {
                            userId = user.id;
                        })

                        fetch("/api/booking/")
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error(response.message);
                                }
                                return response.json()

                            }).then(data => {
                            data.forEach(bookingTimes => {
                                const scheduleId = bookingTimes.bookingUserCouch.schedule_id;
                                const bookingUserId = bookingTimes.bookingUserCouch.user.id;
                                if (scheduleDay.id === scheduleId) {
                                    if (bookingUserId === userId) {
                                        dayButton.style.backgroundColor = 'green';
                                    } else {
                                        dayButton.style.backgroundColor = 'yellow';
                                    }
                                }
                            })
                        })

                        const listener = function () {
                            const allTime = document.getElementById("times-record");
                            allTime.innerHTML = '';
                            showDetailsBooking(scheduleDay, dayButton);
                        }
                        const showTime = function () {
                            const infoBoxes = document.querySelectorAll("div.info-box");
                            infoBoxes.forEach(box => box.remove());
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
                    const timeButton = document.createElement('buttonTime');
                    timeButton.classList.add('buttonTime');
                    timeButton.textContent = time;
                    timeButton.style.backgroundColor = 'greenYellow';

                    // timeButton.setAttribute('buttonTimeId',scheduleDay.value);

                    fetch("/api/booking/getAllBookingUser/")
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(response.message);
                            }
                            return response.json()

                        }).then(data => {
                        data.forEach(bookingTimes => {
                            const scheduleId = bookingTimes.bookingUserCouch.schedule_id;
                            const bookingUserId = bookingTimes.bookingUserCouch.user.id
                            if (scheduleDay.id === scheduleId) {
                                if (bookingUserId === userId) {
                                    dayButton.style.backgroundColor = 'green';
                                    timeButton.style.backgroundColor = 'green';
                                }
                            }
                        })
                    })
                    const listener = function () {
                        showDetailsBooking(scheduleDay, timeButton);

                    }
                    timeButton.addEventListener("click", listener);
                    timeSlots.appendChild(timeButton);
                }
            }
        })
    });
    bookingTime.classList.remove('hidden');

}

function showDetailsBooking(scheduleDay, dayButton) {
    const coach = document.getElementById("coach").value

    // Создаем или показываем окно с информацией
    const infoBoxes = document.querySelectorAll("div.info-box");
    infoBoxes.forEach(box => box.remove());

    let infoBox = document.createElement('div');
    infoBox.classList.add('info-box');
    infoBox.display = 'flex';


    const time = new Date(scheduleDay.date).toLocaleTimeString(this.time);
    const description = scheduleDay.description || "Комментарий отсутствует";
    const place = scheduleDay.place || "Место не указано";
    const sum = scheduleDay.sum || "Сумма не указана";
    const scheduleId = scheduleDay.id;

    const trainingTime = new Date(scheduleDay.date);
    const now = new Date();
    const msDiff = trainingTime - now;
    let hoursDiff = msDiff / (1000 * 60 * 60);
    hoursDiff = Math.round(hoursDiff * 10) / 100;

    console.log(msDiff);
    console.log(hoursDiff);

    if (trainingTime < now) {
        // Тренировка уже прошла
        infoBox.innerHTML = `
            <p><strong>Время:</strong> ${time}</p>
            <p><strong>Место:</strong> ${place}</p>
            <p><strong>Сумма:</strong> ${sum}</p>
            <p><strong>Комментарий:</strong> ${description}</p>
            <p style="color:red;"><strong>Тренировка уже завершена</strong></p>
            <button id="closeInfoBox" style="width: 95%">Закрыть</button>
        `;


        document.body.appendChild(infoBox);
        document.getElementById('closeInfoBox').addEventListener('click', function () {
            document.body.removeChild(infoBox);
        });

        // Позиционируем окно рядом с кнопкой дня
        const rect = dayButton.getBoundingClientRect();
        infoBox.style.top = `${rect.bottom + window.scrollY}px`;
        infoBox.style.left = `${rect.left + window.scrollX}px`;

        return;
    }


    fetch("/api/booking/")
        .then(response => {
            if (!response.ok) {
                throw new Error(response.message);
            }
            return response.json()

        }).then(data => {
        let count = 0;
        data.forEach(bookingTimes => {
            const scheduleId = bookingTimes.bookingUserCouch.schedule_id;
            if (scheduleDay.id === scheduleId) {
                count++;
            }
        })
        console.log(count);


        infoBox.innerHTML = `
        <p><strong>Время:</strong> ${time}</p>
        <p><strong>Место:</strong> ${place}</p>
        <p><strong>Сумма:</strong> ${sum}</p>
        <p><strong>Комментарий:</strong> ${description}</p>`;


        if (dayButton.style.backgroundColor === "green") {
            if (hoursDiff > 2) {
                infoBox.innerHTML += `<button id="bookButtonCancel" style="width: 95%">Отменить</button>`;
            } else {
                infoBox.innerHTML += `<p style="color: red"><strong>Отмена менее чем за 2 часа невозможна</strong></p>`;
            }
            infoBox.innerHTML += `<button id="closeInfoBox" style="width: 95%">Закрыть</button>`;
        } else if (dayButton.style.backgroundColor === "yellow" && scheduleDay.typeWorkout === "individual") {
            if (count <= 1) {
                infoBox.innerHTML += `
                <button id="bookButton" style="width: 95%">Забронировать</button>
                <button id="closeInfoBox" style="width: 95%">Закрыть</button>`;
            } else {
                infoBox.innerHTML += `<p style="color:red"><strong>Нет свободных мест</strong></p>
                                  <button id="closeInfoBox" style="width: 95%">Закрыть</button>`;
            }
        }else {
            infoBox.innerHTML += `
            <button id="bookButton" style="width: 95%">Забронировать</button>
            <button id="closeInfoBox" style="width: 95%">Закрыть</button>`;
        }

        // Добавляем окно
        document.body.appendChild(infoBox);

        // Обработчики
        const cancelBtn = document.getElementById('bookButtonCancel');
        if (cancelBtn) {
            cancelBtn.addEventListener('click', function () {
                bookButtonCancel(scheduleId, infoBox);
            });
        }

        const bookBtn = document.getElementById('bookButton');
        if (bookBtn) {
            bookBtn.addEventListener('click', function () {
                bookTraining(scheduleId, infoBox, sum, coach);
            });
        }

        const closeBtn = document.getElementById('closeInfoBox');
        if (closeBtn) {
            closeBtn.addEventListener('click', function () {
                document.body.removeChild(infoBox);
            });
        }
    });
    // Позиционируем окно рядом с кнопкой дня
    const rect = dayButton.getBoundingClientRect();
    infoBox.style.top = `${rect.bottom + window.scrollY}px`;
    infoBox.style.left = `${rect.left + window.scrollX}px`;


}

function bookButtonCancel(scheduleId, infoBox) {
    fetch("/api/booking/schedule/" + scheduleId, {
        method: "DELETE",
        headers: {'Content-Type': 'application/json'},

    })
        .then(response => {
            getSchedule();
            document.body.removeChild(infoBox);
            if (!response.ok) {
                throw new Error(response.message);
            }
            return response.json();
        })

}

function transactionForCouch(couchId, sum) {
    fetch("api/balance/transfer/user/couch/" + couchId, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({sum: sum})
    }).then(response => {
        if (!response.ok) {
            throw new Error(response.message);
        }
        return response.json();
    }).then(date => {
        alert("Деньги успешно переведены")
    }).catch(error => {
        console.error("Ошибка при переводе:", error);
    });
}

function bookTraining(scheduleId, infoBox, sum, coach) {

    fetch("/api/booking/", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({schedule_id: scheduleId})
    })
        .then(async response => {
            getSchedule();
            if (response.status === 402) {
                // Недостаточно средств
                throw new Error("Недостаточно средств для бронирования");

            } else if (!response.ok) {
                throw new Error("Ошибка при бронировании");
            }
            alert("Бронь успешно добавлена!");
            transactionForCouch(coach, sum);
            return response.json();

        })
        .then(data => {
            const timeSlots = document.getElementById("times-record");
            timeSlots.innerHTML = '';
            if (infoBox && document.body.contains(infoBox)) {
                document.body.removeChild(infoBox);
            }
        })
        .catch(error => {
            console.error("Ошибка при бронировании:", error);
            alert("Не хватает баланса")
            if (infoBox && document.body.contains(infoBox)) {
                document.body.removeChild(infoBox);
            }
        });
}


