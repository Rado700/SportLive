document.addEventListener('DOMContentLoaded', function() {
    const url ="/api/organisation/";
    function setOrganisation (data){
        const getAllOrganisation = document.getElementById("organisationType");
        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.id;
            option.textContent = item.name;
            getAllOrganisation.appendChild(option);
        })
    }

    fetch(url)
        .then(response =>{
            if(!response.ok){
                throw new Error("Неверная организация")
            }
            return response.json();
        })
        .then(data => setOrganisation(data))
        .catch(error => console.error(error));
});

const getAllOrganisation = document.getElementById("organisationType");

getAllOrganisation.addEventListener('change', function() {
    const url ="/api/couch/organisation/" + getAllOrganisation.value;
    function setCouch (data){
        const getAllCouch = document.getElementById("couchType");
        getAllCouch.innerHTML = '<option value="couch">Тренер</option>';
        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.id;
            option.textContent = item.name;
            getAllCouch.appendChild(option);
        })
    }

    fetch(url)
        .then(response =>{
            if(!response.ok){
                throw new Error("Невырная организация")
            }
            return response.json();
        })
        .then(data => setCouch(data))
        .catch(error => console.error(error));
});


