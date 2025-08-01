import { name } from './form.js';

const view = document.querySelector('#view-form-error');

export default function errorView(status) {
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');

    console.log(status);

    const userErrorName = view.querySelector('#error-name');
    userErrorName.textContent = name.value;

    const errorMessageEl = view.querySelector('#error-message');
    let errorMessageContent =
        'stiamo riscontrando dei problemi con il server.  Torneremo presto online. Riprova tra poco';

    if (!status) {
        return;
    }

    switch (status) {
        case 400:
            errorMessageContent =
                'sembra che ci siano degli errori nei dati che hai mandato. Riprova';
            break;
        case 403:
            errorMessageContent =
                'il concorso non è attivo. Il concorso inizia alle 9:00 del 01/07 e termina alle 9:00 del 08/07 alle 9:00';
            break;

        default:
            break;
    }
    errorMessageEl.textContent = errorMessageContent;
}
