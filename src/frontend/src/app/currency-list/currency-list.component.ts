import {Component, OnDestroy, OnInit} from '@angular/core';
import {DatePipe, NgForOf} from "@angular/common";
import {CurrencyService} from "../services/currency-service";
import {EventBusService} from "../event-bus/event-bus";
import {Subscription} from "rxjs";
import {AuditDto} from "../interfaces/AuditDto";

@Component({
  selector: 'currency-list',
  template: `
    <div class="currency-list-container">
      <h2>Saved Currency Requests</h2>
      <table>
        <thead>
        <tr>
          <th>Name</th>
          <th>Currency</th>
          <th>Date</th>
          <th>Value</th>
        </tr>
        </thead>
        <tbody>
        <tr *ngFor="let request of currencyRequests">
          <td>{{ request.name }}</td>
          <td>{{ request.currency }}</td>
          <td>{{ request.dateTime | date: 'medium' }}</td>
          <td>{{ request.value }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  `,
  standalone: true,
  imports: [
    DatePipe,
    NgForOf
  ],
  styleUrls: ['./currency-list.component.css']
})
export class CurrencyListComponent implements OnInit, OnDestroy {
  currencyRequests: AuditDto[] = [];
  private eventsSubscription: Subscription | null = null;

  constructor(private currencyService: CurrencyService, private eventBus: EventBusService) {
  }

  ngOnInit() {
    this.loadCurrencyRequests();

    this.eventsSubscription = this.eventBus.on().subscribe(event => {
      if (event.type === 'CURRENCY_UPDATED') {
        this.loadCurrencyRequests();
      }
    });
  }

  ngOnDestroy() {
    if (this.eventsSubscription) {
      this.eventsSubscription.unsubscribe();
    }
  }

  async loadCurrencyRequests() {
    try {
      this.currencyRequests = await this.currencyService.getAllRequests();
    } catch (error) {
      console.error('Error loading currency requests:', error);
    }
  }
}
