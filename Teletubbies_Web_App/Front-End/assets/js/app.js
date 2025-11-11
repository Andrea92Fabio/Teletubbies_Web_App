import ageCheck from './age-check.js';
import form from './form.js';
import home from './home.js';

ageCheck();

// Seleziona tutti i pulsanti con la classe 'join-button'
const joinButtons = document.querySelectorAll('.join-button');

// Aggiungi un gestore di eventi click a ciascun pulsante
joinButtons.forEach(button => {
    button.addEventListener('click', (e) => {
        e.preventDefault();
        // Nascondi la vista della home
        const homeView = document.querySelector('#view-home');
        homeView.classList.remove('active');
        homeView.setAttribute('inert', '');
        homeView.setAttribute('aria-hidden', true);

        // Chiama la funzione form() per rendere visibile la vista del form
        form();
    });
});
