import { test, expect } from '@playwright/test';

test('sidan laddar och visar uppgifterna från API:et', async ({ page }) => {
  await page.goto('/');

  await expect(page.getByRole('heading', { name: 'Mina uppgifter' })).toBeVisible();
  await expect(page.locator('#task-list')).toContainText('gurka');
  await expect(page.locator('#task-list')).toContainText('mjölk');
});