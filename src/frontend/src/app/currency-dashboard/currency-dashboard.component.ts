import {Component} from '@angular/core';
import {CurrencyListComponent} from "../currency-list/currency-list.component";
import {CurrencyFormComponent} from "../currency-form/currency-form.component";


@Component({
  selector: 'currency-dashboard',
  template: `
    <div class="currency-dashboard">
      <h1 class="header">Currency Dashboard</h1>
      <currency-form></currency-form>
      <currency-list></currency-list>
    </div>`,
  standalone: true,
  imports: [
    CurrencyListComponent,
    CurrencyFormComponent
  ],
  styleUrls: ['./currency-dashboard.component.css']
})
export class CurrencyDashboardComponent {
  constructor() {
  }
}
