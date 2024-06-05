import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventBusService {
  private eventBus = new Subject<any>();

  emit(event: any) {
    this.eventBus.next(event);
  }

  on() {
    return this.eventBus.asObservable();
  }
}
