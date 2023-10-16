import { IAppCourseMySuffix } from 'app/shared/model/app-course-my-suffix.model';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface IAppAvailableCourseMySuffix {
  id?: number;
  courses?: IAppCourseMySuffix | null;
  users?: IAppUserMySuffix[] | null;
}

export const defaultValue: Readonly<IAppAvailableCourseMySuffix> = {};
