const params = new URLSearchParams(window.location.search);
const tokenId = params.get('tokenId');
const messageTarget = document.querySelector('#result-message');
const wrapper = document.querySelector('#result-wrapper');

displayResult(await getResultFromServer(tokenId));

async function getResultFromServer(tokenId) {
    const response = await fetch(
        `http://192.168.100.33:80/api/confirmation/${tokenId}`
    );

    const retvalue = await response.json();
    console.log(retvalue.message);
    return retvalue;
}

function displayResult(result) {
    console.log(result.message);

    if (result.message == 'you win') {
        messageTarget.textContent = 'Complimenti! Hai vinto';
        wrapper.classList.add('win');
    } else {
        messageTarget.textContent =
            "Purtroppo sei arrivato tardi. Ma non abbatterti! Puoi sempre vincere l'estrazione finale";
        wrapper.classList.add('lose');
    }
}
