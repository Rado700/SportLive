let chatID;

try {
    chatID = window.Telegram.WebApp.initDataUnsafe.user.id;
} catch (error) {
    chatID =  482133255;
}

function enterByTg (hashTgId){
    fetch("/api/login/tg/enter/", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({tgId:hashTgId})
    })
        .then(response => {
            return response.text();
        }).then(data => {
        window.location.href = data + window.location.search;
    })
}

async function hashString(str) {
    const encoder = new TextEncoder();
    const data = encoder.encode(str);
    const hashBuffer = await crypto.subtle.digest('SHA-256', data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
}
hashString(`${chatID}`).then(hash => enterByTg(hash));