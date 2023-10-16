import dayjs from 'dayjs';
import { IAppOrderItemMySuffix } from 'app/shared/model/app-order-item-my-suffix.model';
import { IAppPaymentMySuffix } from 'app/shared/model/app-payment-my-suffix.model';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface IAppOrderMySuffix {
  id?: number;
  total?: number | null;
  createdAt?: string | null;
  status?: string | null;
  paymentID?: number | null;
  orderItems?: IAppOrderItemMySuffix[] | null;
  payments?: IAppPaymentMySuffix[] | null;
  user?: IAppUserMySuffix | null;
}

export const defaultValue: Readonly<IAppOrderMySuffix> = {};
