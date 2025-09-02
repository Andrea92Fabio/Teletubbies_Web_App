import { test, expect } from '@playwright/test';

test('completes the registration flow successfully', async ({ page }) => {
    // 1. Apri la pagina iniziale
    console.log('PASSAGGIO 1: Vado alla pagina iniziale.');
    await page.goto('http://localhost:8081');

    // Attendi che la vista 'age-check' diventi attiva e compila il campo
    console.log('PASSAGGIO 2: Compilo il form di controllo dell\'età.');
    await page.waitForSelector('#view-age-check.active');
    await page.locator('#age-check-birthdate').fill('1992-07-31');

    // Invia il form per superare il controllo dell'età cliccando sul bottone
    console.log('PASSAGGIO 3: Clicco sul bottone "Entra".');
    await page.getByRole('button', { name: 'Entra' }).click();

    // 2. Dopo aver superato il controllo, attendi che la vista 'age-check' non sia più visibile.
    console.log('PASSAGGIO 4: Attendo che la vista "age-check" non sia più visibile.');
    await page.locator('#view-age-check').waitFor({ state: 'hidden' });

    // 2.2 Dopo aver superato il controllo, attendi che la vista 'home' sia visibile.
    console.log('PASSAGGIO 4.2: Attendo che la vista "home" diventi visibile.');
    await page.locator('#view-home').waitFor({ state: 'visible' });

    // 3. Clicca sul pulsante per partecipare al concorso
    console.log('PASSAGGIO 5: Clicco sul pulsante "Partecipa ora".');
    await page.locator('.join-button').first().click();

    // 4. Dopo aver cliccato, attendi che la vista 'form' sia visibile.
    console.log('PASSAGGIO 6: Attendo che la vista del form di registrazione diventi visibile.');
    await page.locator('#view-form').waitFor({ state: 'visible' });

    // 5. Compila il form di registrazione
    console.log('PASSAGGIO 7: Inizio la compilazione del form di registrazione.');
    await page.locator('#form-name').fill('Andrea');
    await page.locator('#form-surname').fill('Grossi');
    await page.locator('#form-email').fill('xhunterx92@gmail.com');
    await page.locator('#form-telephone').fill('3408461295');
    await page.locator('#form-gender').selectOption('man');
    await page.locator('#form-residency-country').selectOption('italy');
    await page.locator('#form-residency-province').selectOption('MI');
    await page.locator('#form-residency-address').fill('Via Enrico Berlinguer, 11');
    await page.locator('#form-residency-zip-code').fill('20031');
    await page.locator('#form-shipping-country').selectOption('italy');
    await page.locator('#form-shipping-province').selectOption('MI');
    await page.locator('#form-shipping-address').fill('Via Enrico Berlinguer, 11');
    await page.locator('#form-shipping-zip-code').fill('20031');
    await page.locator('#form-fiscal-code').fill('GRSNRF92L31F205T');
    
    // Spunta le caselle per regole e privacy
    console.log('PASSAGGIO 8: Spunto le caselle di regole e privacy.');
    await page.locator('#form-rules').check();
    await page.locator('#form-privacy').check();

    // 6. Invia il form
    console.log('PASSAGGIO 9: Clicco sul pulsante di invio del form.');
    await page.getByRole('button', { name: 'invia' }).click();

    // 7. Verifica che il messaggio di ringraziamento sia visibile
    console.log('PASSAGGIO 10: Verifico che la pagina di ringraziamento sia visibile.');
    await expect(page.locator('#view-thank-you')).toBeVisible();

    console.log('TEST COMPLETATO CON SUCCESSO!');
});