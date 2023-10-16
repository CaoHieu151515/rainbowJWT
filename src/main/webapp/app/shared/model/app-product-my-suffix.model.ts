import { IAppCategoryMySuffix } from 'app/shared/model/app-category-my-suffix.model';
import { IAppProductImageMySuffix } from 'app/shared/model/app-product-image-my-suffix.model';
import { IAppCartMySuffix } from 'app/shared/model/app-cart-my-suffix.model';

export interface IAppProductMySuffix {
  id?: number;
  name?: string | null;
  price?: number | null;
  unit?: number | null;
  description?: string | null;
  status?: string | null;
  category?: IAppCategoryMySuffix | null;
  images?: IAppProductImageMySuffix | null;
  carts?: IAppCartMySuffix[] | null;
}

export const defaultValue: Readonly<IAppProductMySuffix> = {};
