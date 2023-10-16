import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface IAppCourseMySuffix {
  id?: number;
  name?: string | null;
  level?: string | null;
  image?: string | null;
  courses?: IAppLessonMySuffix[] | null;
  users?: IAppUserMySuffix[] | null;
}

export const defaultValue: Readonly<IAppCourseMySuffix> = {};
