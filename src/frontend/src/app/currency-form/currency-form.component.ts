import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {RouterOutlet} from "@angular/router";
import {CurrencyService} from "../services/currency-service";
import {EventBusService} from "../event-bus/event-bus";
import {CurrencyRequest} from "../interfaces/CurrencyRequest";
import {RateValue} from "../interfaces/RateValue";

@Component({
  selector: 'currency-form',
  imports: [RouterOutlet, FormsModule],
  template: `
    <div class="currency-form">
      <h2>Get Currency Value</h2>
      <form (ngSubmit)="submitCurrencyRequest()">
        <div>
          <label for="currencyCode">Currency Code:</label>
          <input id="currencyCode" type="text" [(ngModel)]="currencyCode" name="currencyCode" required placeholder="e.g., EUR">
        </div>
        <div>
          <label for="userName">Your Name:</label>
          <input id="userName" type="text" [(ngModel)]="userName" name="userName" required placeholder="e.g., John Doe">
        </div>
        <button type="submit">Submit</button>
      </form>
      <p class="response">{{ responseMessage }}</p>
    </div>`,
  standalone: true,
  styleUrls: ['./currency-form.component.css']
})
export class CurrencyFormComponent {
  currencyCode: string = '';
  userName: string = '';
  responseMessage: string = '';

  constructor(private currencyService: CurrencyService, private eventBus: EventBusService) {
  }

  async submitCurrencyRequest() {
    const requestBody: CurrencyRequest = {
      currencyCode: this.currencyCode,
      name: this.userName
    };

    try {
      const response: RateValue = await this.currencyService.getCurrentCurrencyValue(requestBody);
      this.responseMessage = `The current value of 1 ${this.currencyCode.toUpperCase()} is ${response.value} PLN.`;
    } catch (error: any) {
      this.responseMessage = error;
    } finally {
      this.eventBus.emit({type: 'CURRENCY_UPDATED'});
    }
  }
}
