import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';
import { IAppProductMySuffix } from 'app/shared/model/app-product-my-suffix.model';

export interface IAppOrderItemMySuffix {
  id?: number;
  quantity?: number | null;
  price?: number | null;
  unit?: string | null;
  note?: string | null;
  order?: IAppOrderMySuffix | null;
  product?: IAppProductMySuffix | null;
}

export const defaultValue: Readonly<IAppOrderItemMySuffix> = {};
