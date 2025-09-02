import { test, expect } from '@playwright/test';

test('completes the registration flow successfully', async ({ page }) => {
    // 1. Apri la pagina iniziale
    await page.goto('http://localhost:8081');

    // Attendi che la vista 'age-check' diventi attiva e compila il campo
    await page.waitForSelector('#view-age-check.active');
    await page.locator('#age-check-birthdate').fill('1992-07-31');

    // Invia il form per superare il controllo dell'età
    await page.locator('#age-check-form').evaluate(form => form.submit());

    // 2. Dopo aver superato il controllo, attendi che la vista 'home' sia visibile.
    await page.locator('#view-home').waitFor({ state: 'visible' });

    // 3. Clicca sul pulsante per partecipare al concorso
    await page.locator('.cta').first().click();

    // 4. Dopo aver cliccato, attendi che la vista 'form' sia visibile.
    // Usiamo 'state: "visible"' che è il modo più affidabile
    // per attendere che l'elemento sia pronto.
    await page.locator('#view-form').waitFor({ state: 'visible' });

    // 5. Compila il form di registrazione
    await page.locator('#form-name').fill('Andrea');
    await page.locator('#form-surname').fill('Grossi');
    await page.locator('#form-email').fill('xhunterx92@gmail.com');
    await page.locator('#form-telephone').fill('3408461295');
    await page.locator('#form-gender').selectOption('male');
    await page.locator('#form-residency-country').selectOption('italy');
    await page.locator('#form-residency-province').fill('MI');
    await page.locator('#form-residency-address').fill('Via Enrico Berlinguer, 11');
    await page.locator('#form-residency-zip-code').fill('20031');
    await page.locator('#form-shipping-country').selectOption('italy');
    await page.locator('#form-shipping-province').fill('MI');
    await page.locator('#form-shipping-address').fill('Via Enrico Berlinguer, 11');
    await page.locator('#form-shipping-zip-code').fill('20031');
    await page.locator('#form-fiscal-code').fill('GRSNRF92L31F205T');
    
    // Spunta le caselle per regole e privacy
    await page.locator('#form-rules').check();
    await page.locator('#form-privacy').check();

    // 6. Invia il form
    await page.locator('#main-form button[type="submit"]').click();

    // 7. Verifica che il messaggio di ringraziamento sia visibile
    await expect(page.locator('#thank-you-view')).toBeVisible();
});