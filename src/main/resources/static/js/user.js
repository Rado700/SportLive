document.addEventListener('DOMContentLoaded', () => {
  const mainScreen = document.getElementById('main-screen');
  const profileScreen = document.getElementById('profile-screen');
  const infoScreen = document.getElementById('info-screen');
  const stopwatchScreen = document.getElementById('stopwatch-screen');

  const profileForm = document.getElementById('profile-form');
  const profileInfo = document.getElementById('profile-info span');
  const trainerInfo = document.getElementById('trainer-info span');
  const equipmentInfo = document.getElementById('equipment-info span');

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
      screen.classList.remove('hidden');
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
              profileInfo.textContent = `${data.name} ${data.surname}, ${data.height} cm, ${data.weight} kg (Дата обновления: ${data.date})`;
              trainerInfo.textContent = data.remainingSessions;
              equipmentInfo.textContent = data.equipmentCount;
          });
  });

  document.getElementById('stopwatch').addEventListener('click', () => {
      showScreen(stopwatchScreen);
  });

  profileForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const formData = new FormData(profileForm);
      const profileData = {
          name: formData.get('firstName'),
          surname: formData.get('lastName'),
          height: formData.get('height'),
          weight: formData.get('weight')
      };
      // Send data to backend
      fetch('/user/2', {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(profileData)
      })
      .then(response => response.json())
      .then(data => {
          alert('Данные профиля обновлены');
          profileForm.reset();
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
          body: JSON.stringify({ notes })
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
});
