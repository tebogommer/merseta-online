import { Page, Locator } from '@playwright/test';

export class WspPage {
  readonly page: Page;
  readonly orgIdInput: Locator;
  readonly numEmployeesInput: Locator;
  readonly totalPayrollInput: Locator;
  readonly totalTrainingCostsInput: Locator;
  readonly submitButton: Locator;
  readonly errorMessageTrainingCosts: Locator;
  readonly errorMessageGeneral: Locator;

  constructor(page: Page) {
    this.page = page;
    this.orgIdInput = page.locator('input[name="orgId"]');
    this.numEmployeesInput = page.locator('input[name="numberOfEmployees"]');
    this.totalPayrollInput = page.locator('input[name="totalPayroll"]');
    this.totalTrainingCostsInput = page.locator('input[name="totalTrainingCosts"]');
    this.submitButton = page.getByRole('button', { name: /Save to Draft|Submit/i });
    
    // Hardcoded text locators based on known UI constraints
    this.errorMessageTrainingCosts = page.locator('text=Total Training Costs cannot exceed the Total Company Payroll.');
    this.errorMessageGeneral = page.locator('text=Please correct the financial and target metric errors before submission.');
  }

  async gotoNewWsp(orgId: number) {
    await this.page.goto(`/workplace-skills-plans/new?orgId=${orgId}`);
  }

  async fillFinancials(employees: string, payroll: string, trainingCosts: string) {
    await this.numEmployeesInput.fill(employees);
    await this.totalPayrollInput.fill(payroll);
    await this.totalTrainingCostsInput.fill(trainingCosts);
  }

  async submit() {
    await this.submitButton.click();
  }
}
