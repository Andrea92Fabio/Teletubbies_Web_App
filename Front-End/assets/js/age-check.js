import home, { observer } from './home.js';

export default function ageCheck() {
    const view = document.querySelector('#view-age-check');

    const animatableOnEntranceObjs = view.querySelectorAll('.fade-in-top');

    animatableOnEntranceObjs.forEach((el) => observer.observe(el));

    let birthdate = sessionStorage.getItem('birthdate');
    if (birthdate != null && isDrinkingAge(new Date(birthdate))) {
        home();
        return;
    }
    view.classList.add('active');
    const form = document.querySelector('#age-check-form');
    const birthdateInput = document.querySelector('#age-check-birthdate');
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const birthdateObj = new Date(birthdateInput.value);
        sessionStorage.setItem('birthdate', birthdateObj.toISOString());
        if (isDrinkingAge(birthdateObj)) {
            view.classList.remove('active');
            home();
        } else {
            window.location.replace('https://www.beviresponsabile.it/');
        }
    });
}

export function isDrinkingAge(birthdate) {
    const today = new Date();

    if (
        today.getFullYear() - birthdate.getFullYear() > 18 &&
        today.getFullYear() - birthdate.getFullYear() < 100
    ) {
        return true;
    } else if (
        today.getFullYear() - birthdate.getFullYear() < 18 ||
        today.getFullYear() - birthdate.getFullYear() >= 100
    ) {
        return false;
    } else if (today.getMonth() < birthdate.getMonth()) {
        return false;
    } else if (today.getMonth() > birthdate.getMonth()) {
        return true;
    }

    return today.getDate() >= birthdate.getDate();
}
