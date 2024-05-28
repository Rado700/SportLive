document.addEventListener('DOMContentLoaded', () => {
    const mainScreen = document.getElementById('main-screen');
    const profileScreen = document.getElementById('profile-screen');
    const infoScreen = document.getElementById('info-screen');
    const stopwatchScreen = document.getElementById('stopwatch-screen');
    const exercisesScreen = document.getElementById('exercises-screen');

    const profileForm = document.getElementById('profile-form');
    const exercisesForm = document.getElementById('exercises-form');
    const sessionsInfo = document.getElementById('sessions-info').querySelector('span');
    const equipmentInfo = document.getElementById('equipment-info').querySelector('span');

    const stopwatchDisplay = document.getElementById('stopwatch-display');
    let stopwatchInterval;
    let stopwatchSeconds = 0;

    const timerDisplay = document.getElementById('timer-display');
    let timerInterval;

    const showScreen = (screen) => {
        document.querySelectorAll('.screen').forEach(screen => screen.classList.remove('active'));
        screen.classList.add('active');
    };

    document.getElementById('profile').addEventListener('click', () => {
        showScreen(profileScreen);
    });

    document.getElementById('info').addEventListener('click', () => {
        showScreen(infoScreen);
        // Fetch and display data from backend
        fetch('/api/info')
            .then(response => response.json())
            .then(data => {
                sessionsInfo.textContent = data.remainingSessions;
                equipmentInfo.textContent = data.equipmentCount;
            });
    });

    document.getElementById('stopwatch').addEventListener('click', () => {
        showScreen(stopwatchScreen);
    });

    document.getElementById('notes').addEventListener('click', () => {
        showScreen(stopwatchScreen);
    });

    document.getElementById('add-activity').addEventListener('click', () => {
        // Open calendar modal or other functionality for adding activities
        alert('Function to add activity');
    });

    document.getElementById('add-equipment').addEventListener('click', () => {
        alert('Function to add equipment');
    });

    document.getElementById('add-exercises').addEventListener('click', () => {
        showScreen(exercisesScreen);
    });

    profileForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const name = document.getElementById('name').value;
        const orgId = document.getElementById('org-id').value;
        fetch('/api/profile', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, orgId })
        })
        .then(response => response.json())
        .then(data => {
            alert('Профиль сохранен');
        });
    });

    exercisesForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const exercises = [];
        for (let i = 1; i <= 10; i++) {
            exercises.push(document.getElementById(`exercise-${i}`).value);
        }
        fetch('/api/exercises', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ exercises })
        })
        .then(response => response.json())
        .then(data => {
            alert('Упражнения сохранены');
        });
    });

    document.getElementById('start-stopwatch').addEventListener('click', () => {
        if (stopwatchInterval) clearInterval(stopwatchInterval);
        stopwatchInterval = setInterval(() => {
            stopwatchSeconds++;
            const hours = Math.floor(stopwatchSeconds / 3600);
            const minutes = Math.floor((stopwatchSeconds % 3600) / 60);
            const seconds = stopwatchSeconds % 60;
            stopwatchDisplay.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
        }, 1000);
    });

    document.getElementById('stop-stopwatch').addEventListener('click', () => {
        clearInterval(stopwatchInterval);
    });

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
        fetch('/api/notes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ notes })
        })
        .then(response => response.json())
        .then(data => {
            alert('Заметки сохранены');
            document.getElementById('notes').value = '';
        });
    });

    document.querySelectorAll('.back-button').forEach(button => {
        button.addEventListener('click', () => {
            showScreen(mainScreen);
        });
    });
});
