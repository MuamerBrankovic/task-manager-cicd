import { test, expect } from '@playwright/test';

test('sidan laddar och visar uppgifterna från API:et', async ({ page }) => {
  await page.goto('/');

  await expect(page.getByRole('heading', { name: 'Mina uppgifter' })).toBeVisible();
  await expect(page.locator('#task-list')).toContainText('gurka');
  await expect(page.locator('#task-list')).toContainText('mjölk');
});

test('markera en uppgift som klar två gånger ger felmeddelande', async ({ page }) => {
  await page.goto('/');

  const taskName = `test-uppgift-${Date.now()}`;

  await page.locator('#task-name-input').fill(taskName);
  await page.getByRole('button', { name: 'Lägg till' }).click();

  const row = page.locator('#task-list li', { hasText: taskName });
  await row.getByRole('button', { name: 'Klar' }).click();

  await expect(page.locator('#task-list li.done', { hasText: taskName })).toBeVisible();

  await page.locator('#task-list li.done', { hasText: taskName })
    .getByRole('button', { name: 'Klar' })
    .click();

  await expect(page.locator('#error-message')).not.toBeEmpty();
});