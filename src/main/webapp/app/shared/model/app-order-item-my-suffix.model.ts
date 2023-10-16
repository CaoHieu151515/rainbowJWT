import { IAppProductMySuffix } from 'app/shared/model/app-product-my-suffix.model';
import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';

export interface IAppOrderItemMySuffix {
  id?: number;
  quantity?: number | null;
  price?: number | null;
  unit?: string | null;
  note?: string | null;
  product?: IAppProductMySuffix | null;
  order?: IAppOrderMySuffix | null;
}

export const defaultValue: Readonly<IAppOrderItemMySuffix> = {};
