import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { IAppProductMySuffix } from 'app/shared/model/app-product-my-suffix.model';

export interface IAppCartMySuffix {
  id?: number;
  quantity?: number | null;
  user?: IAppUserMySuffix | null;
  products?: IAppProductMySuffix[] | null;
}

export const defaultValue: Readonly<IAppCartMySuffix> = {};
