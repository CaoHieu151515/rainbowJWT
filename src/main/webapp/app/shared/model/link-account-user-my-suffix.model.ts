import { IUser } from 'app/shared/model/user.model';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface ILinkAccountUserMySuffix {
  id?: number;
  user?: IUser | null;
  appUser?: IAppUserMySuffix | null;
}

export const defaultValue: Readonly<ILinkAccountUserMySuffix> = {};
