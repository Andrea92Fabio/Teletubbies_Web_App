// playwright.config.js
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  testDir: './tests',              // cartella dove si trovano i test
  timeout: 30 * 1000,              // timeout globale (30 secondi per test)
  expect: {
    timeout: 5000                  // timeout delle aspettative (es. toBeVisible)
  },
  reporter: 'html',                // reporter HTML
  use: {
    headless: true,                // esegui i test in modalità headless
    viewport: { width: 1280, height: 720 },
    actionTimeout: 0,              // nessun timeout predefinito per le singole azioni
    ignoreHTTPSErrors: true,
    video: 'retain-on-failure',    // registra video solo in caso di fallimento
    screenshot: 'only-on-failure'  // screenshot solo se fallisce
  },
});
