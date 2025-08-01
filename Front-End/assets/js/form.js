import { observer } from './home.js';
import { isDrinkingAge } from './age-check.js';
import thankYou from './thank-you.js';
import errorView from './error-view.js';

const view = document.querySelector('#view-form');
const formElement = view.querySelector('#main-form');

export const name = formElement.querySelector('#form-name');
const surname = formElement.querySelector('#form-surname');
export const email = formElement.querySelector('#form-email');
const birthdate = formElement.querySelector('#form-birthdate');
const phoneNumber = formElement.querySelector('#form-telephone');
const gender = formElement.querySelector('#form-gender');
const residencyCountry = formElement.querySelector('#form-residency-country');
const residencyAddress = formElement.querySelector('#form-residency-address');
const residencyZipCode = formElement.querySelector('#form-residency-zip-code');
const shippingCountry = formElement.querySelector('#form-shipping-country');
const shippingAddress = formElement.querySelector('#form-shipping-address');
const shippingZipCode = formElement.querySelector('#form-shipping-zip-code');
const fiscalCode = formElement.querySelector('#form-fiscal-code');
const residencyProvince = formElement.querySelector('#form-residency-province');
const shippingProvince = formElement.querySelector('#form-shipping-province');
const rules = formElement.querySelector('#form-rules');
const privacy = formElement.querySelector('#form-privacy');

//todo:add provincia for shipping and residency and add phonenumber
const user = {
    name: null,
    surname: null,
    gender: null,
    email: null,
    residencyCountry: null,
    fiscalCode: null,
    residencyZipCode: null,
    residencyAddress: null,
    shipCountry: null,
    shipZipCode: null,
    shipAddress: null,
    privacy: null,
    rules: null,
    birthDate: null,
    phoneNumber: null,
    residencyProvince: null,
    shipProvince: null,
};

export default function form() {
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');

    window.scrollTo(0, 0);

    const inputFields = formElement.querySelectorAll('.input-field');

    view.querySelector('#form-birthdate').value = sessionStorage
        .getItem('birthdate')
        .split('T')[0];

    inputFields.forEach((el) => {
        errors[el.id] = '';
    });
    //todo: implement an extra check on submit
    formElement.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (validateForm()) {
            user.name = name.value;
            user.surname = surname.value;
            user.gender = gender.value;
            user.email = email.value;
            user.residencyCountry = residencyCountry.value;
            user.fiscalCode = fiscalCode.value;
            user.residencyZipCode = residencyZipCode.value;
            user.residencyAddress = residencyAddress.value;
            user.shipCountry = shippingCountry.value;
            user.shipZipCode = shippingZipCode.value;
            user.shipAddress = shippingAddress.value;
            user.birthDate = birthdate.value;
            user.phoneNumber = phoneNumber.value;
            user.residencyProvince = residencyProvince.value;
            user.shipProvince = shippingProvince.value;
            user.rules = rules.value == 'on';
            user.privacy = privacy.value == 'on';

            const jsonUser = JSON.stringify(user);
            console.log(jsonUser);
            try {
                const res = await fetch(
                    'http://192.168.100.33:80/api/submission',
                    {
                        method: 'POST',
                        body: jsonUser,
                        headers: {
                            'Content-type': 'application/json',
                        },
                    }
                );

                view.classList.remove('active');
                if (res.status === 201 || res.status === 409) {
                    thankYou();
                } else {
                    errorView(res.status);
                }
            } catch (error) {
                view.classList.remove('active');
                console.error('Error message', error.message);
                errorView();
            }
        } else {
            view.querySelector('#generic-error-target').textContent =
                'Ci sono degli errori nei dati che hai inserito. Correggili prima di rinviare ';
        }
    });
    //todo: add rules and privacy checker and add it on front end
    function validateForm() {
        const errors = new Map();

        let isValidForm = true;

        errors.set('form-name', null);
        errors.set('form-surname', null);
        errors.set('form-email', null);
        errors.set('form-birthdate', null);
        errors.set('form-gender', null);
        errors.set('form-residency-country', null);
        errors.set('form-residency-address', null);
        errors.set('form-residency-zip-code', null);
        errors.set('form-fiscal-code', null);
        errors.set('form-shipping-country', null);
        errors.set('form-shipping-address', null);
        errors.set('form-shipping-zip-code', null);
        errors.set('form-residency-province', null);
        errors.set('form-shipping-province', null);
        errors.set('form-telephone', null);
        errors.set('form-privacy', null);
        errors.set('form-rules', null);

        const birthdateObject = new Date(
            formElement.querySelector('#form-birthdate').value
        );

        if (!isDrinkingAge(birthdateObject)) {
            isValidForm = false;
            errors.set(
                'form-birthdate',
                'Devi essere maggiorenne per partecipare'
            );
        }
        if (!validateStringNotEmptyOrWhitespaceOnly(name.value)) {
            isValidForm = false;
            errors.set('form-name', 'Il nome inserito non è valido');
        }

        if (!validateStringNotEmptyOrWhitespaceOnly(surname.value)) {
            isValidForm = false;
            errors.set('form-surname', 'Il cognome inserito non è valido');
        }
        //Inserire email
        if (!validateEmail(email.value)) {
            isValidForm = false;
            errors.set('form-email', `L'email inserita non è valida`);
        }
        //inserire paese residenza
        if (!validateCountry(residencyCountry.value)) {
            isValidForm = false;
            errors.set(
                'form-residency-country',
                `Il paese di residenza inserito non è valido`
            );
        }

        if (!validateStringNotEmptyOrWhitespaceOnly(residencyAddress.value)) {
            isValidForm = false;
            errors.set(
                'form-residency-address',
                `L'indirizzo di residenza inserito non è valido`
            );
        }

        if (!validateStringNotEmptyOrWhitespaceOnly(shippingAddress.value)) {
            isValidForm = false;
            errors.set(
                'form-shipping-address',
                `L'indirizzo di spedizione inserito non è valido`
            );
        }
        //cap 5 figures
        if (!validateZipCode(residencyZipCode.value)) {
            isValidForm = false;
            errors.set(
                'form-residency-zip-code',
                `Il CAP che hai inserito non è valido`
            );
        }
        //Codice fiscale
        if (!validateFiscalCode(fiscalCode.value, residencyCountry.value)) {
            isValidForm = false;
            errors.set(
                'form-fiscal-code',
                `Hai inserito un codice fiscale sbagliato`
            );
        }
        //
        if (!validateZipCode(shippingZipCode.value)) {
            isValidForm = false;
            errors.set(
                'form-shipping-zip-code',
                `Il CAP che hai inserito non è valido`
            );
        }
        if (!validateCountry(shippingCountry.value)) {
            isValidForm = false;
            errors.set(
                'form-shipping-country',
                `Il paese di spedizione che hai inserito non è valido`
            );
        }
        if (
            !validateProvince(residencyProvince.value, residencyCountry.value)
        ) {
            isValidForm = false;
            errors.set(
                'form-residency-province',
                `La provincia di residenza selezionata non è valida`
            );
        }
        if (!validateProvince(shippingProvince.value, shippingCountry.value)) {
            isValidForm = false;
            errors.set(
                'form-shipping-province',
                `La provincia di spedizione selezionata non è valida`
            );
        }
        if (!validateGender(gender.value)) {
            isValidForm = false;
            errors.set('form-gender', `Il genere selezionato non è valido`);
        }
        if (!rules.checked) {
            isValidForm = false;
            errors.set('form-rules', `Il campo è obbligatorio`);
        }
        if (!privacy.checked) {
            isValidForm = false;
            errors.set('form-privacy', `Il campo è obbligatorio`);
        }
        if (!validatePhoneNumber(phoneNumber.value)) {
            isValidForm = false;
            errors.set(
                'form-telephone',
                `Il numero di telefono inserito non è valido`
            );
        }

        errors.forEach((el, key) => {
            if (el) {
                formElement.querySelector(`#${key}-errors`).textContent = el;
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.remove('success');
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.add('error');
            } else {
                formElement.querySelector(`#${key}-errors`).textContent = '';
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.remove('error');
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.add('success');
            }
        });

        console.log(errors);

        return isValidForm;
    }

    //todo: todo: send data as json

    const animatableOnEntranceObjs = [
        ...view.querySelectorAll('.fade-in-left'),
        ...view.querySelectorAll('.fade-in-top'),
        ...view.querySelectorAll('.fade-in-right'),
        ...view.querySelectorAll('.fade-in-bottom'),
    ];

    animatableOnEntranceObjs.forEach((el) => observer.observe(el));
}

function validateStringNotEmptyOrWhitespaceOnly(s) {
    if (s === null || s.replace(' ', '') === '') {
        return false;
    }
    return true;
}

function validateZipCode(s) {
    const reg = /^[\d]{5}$/;
    if (reg.test(s)) {
        return true;
    }
    return false;
}

function validateFiscalCode(s, t) {
    console.log(t);

    if (t === 'san marino') {
        return true;
    }

    if (s.length === 16 || s.length === 17) {
        return true;
    }
    return false;
}

function validateEmail(s) {
    const reg = /^[a-zA-Z0-9]{1}[a-zA-Z0-9.]+@[a-zA-Z0-9.]+[.]{1}[a-zA-Z]{2,}$/; //tiberiu.melinescu  @gmail .com
    if (reg.test(s)) {
        return true;
    }
    return false;
}

function validateCountry(s) {
    if (s === 'italy' || s === 'san marino') {
        return true;
    }
    return false;
}
function validateProvince(s, t) {
    if (t === 'san marino') {
        return true;
    }
    if (s.length === 2) {
        return true;
    }
    return false;
}
function validateGender(s) {
    if (s === '') {
        return false;
    }
    return true;
}
function validatePhoneNumber(s) {
    const reg = /^\+*[0-9]{6,15}$/;
    if (reg.test(s)) {
        return true;
    }
    return false;
}
