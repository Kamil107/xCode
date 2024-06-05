import {Injectable} from '@angular/core';
import axios from 'axios';

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


export interface CurrencyRequest {
  currencyCode: String
  name: String
}

export interface RateValue {
  value: number
}

export interface AuditDto {
  currency: string
  name: string
  dateTime: string
  value: number
  success: boolean
}
