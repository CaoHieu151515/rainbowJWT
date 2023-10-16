import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';

export interface IAppPaymentMySuffix {
  id?: number;
  method?: string | null;
  status?: string | null;
  amount?: number | null;
  note?: string | null;
  order?: IAppOrderMySuffix | null;
}

export const defaultValue: Readonly<IAppPaymentMySuffix> = {};
