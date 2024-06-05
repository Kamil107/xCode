import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {CurrencyFormComponent} from "./currency-form/currency-form.component";
import {CurrencyListComponent} from "./currency-list/currency-list.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CurrencyFormComponent, CurrencyListComponent],
  template: `
    <div class="currency-dashboard">
      <h1>Currency Dashboard</h1>
      <currency-form></currency-form>
      <currency-list></currency-list>
    </div>
  `,
  styleUrl: './app.component.css'
})
export class AppComponent {
}
