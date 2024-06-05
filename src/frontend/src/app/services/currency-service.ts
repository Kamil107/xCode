import {Injectable} from '@angular/core';
import axios from 'axios';
import {AuditDto} from "../interfaces/AuditDto";
import {CurrencyRequest} from "../interfaces/CurrencyRequest";
import {RateValue} from "../interfaces/RateValue";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  constructor() {
  }

  async getCurrentCurrencyValue(requestBody: CurrencyRequest): Promise<RateValue> {
    try {
      const response = await axios.post('/api/currencies/get-current-currency-value-command', requestBody);
      return response.data;
    } catch (error: any) {
      throw error.response.data.message
    }
  }

  async getAllRequests(): Promise<Array<AuditDto>> {
    return (await axios.get('/api/currencies/requests')).data;
  }
}
