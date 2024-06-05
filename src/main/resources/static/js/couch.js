document.addEventListener('DOMContentLoaded', () => {
    const stopwatch = document.getElementById('stopwatch');
    const timer = document.getElementById('timer');
    const notesFooter = document.getElementById('notes-footer');
    const backButton = document.getElementById('back-button');
    const remainingSessions = document.getElementById('remaining-sessions');
    const totalEquipment = document.getElementById('total-equipment');
    const nameField = document.getElementById('name');
    const orgIdField = document.getElementById('org-id');

    stopwatch.addEventListener('click', () => {
        alert('Секундомер запущен!');
        // Здесь можно добавить логику запуска секундомера
    });

    timer.addEventListener('click', () => {
        alert('Таймер установлен!');
        // Здесь можно добавить логику установки таймера
    });

    notesFooter.addEventListener('click', () => {
        alert('Заполнение заметки!');
        // Здесь можно добавить логику заполнения заметки
    });

    backButton.addEventListener('click', () => {
        alert('Возврат на предыдущее окно!');
        // Здесь можно добавить логику возврата на предыдущее окно
    });

    function fetchSessions() {
        fetch('https://sportliveapp.ru/api/sessions') // пример API endpoint
            .then(response => response.json())
            .then(data => {
                remainingSessions.textContent = data.remainingSessions;
                totalEquipment.textContent = data.totalEquipment;
            });
    }

    function addEquipment(equipment) {
        fetch('https://sportliveapp.ru/api/equipment', { // пример API endpoint
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(equipment)
        })
            .then(response => response.json())
            .then(data => {
                alert('Инвентарь добавлен');
                fetchSessions(); // Обновить данные после добавления
            });
    }

    function logTraining(training) {
        fetch('https://sportliveapp.ru/api/trainings', { // пример API endpoint
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(training)
        })
            .then(response => response.json())
            .then(data => {
                alert('Тренировка записана');
                fetchSessions(); // Обновить данные после добавления
            });
    }

    fetchSessions();
});
