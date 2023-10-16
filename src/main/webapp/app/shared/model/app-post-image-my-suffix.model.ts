import { IAppPostMySuffix } from 'app/shared/model/app-post-my-suffix.model';

export interface IAppPostImageMySuffix {
  id?: number;
  imageUrl?: string | null;
  post?: IAppPostMySuffix | null;
}

export const defaultValue: Readonly<IAppPostImageMySuffix> = {};
