import dayjs from 'dayjs';
import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';
import { IAppPostMySuffix } from 'app/shared/model/app-post-my-suffix.model';
import { IAppCourseMySuffix } from 'app/shared/model/app-course-my-suffix.model';
import { IAppAvailableCourseMySuffix } from 'app/shared/model/app-available-course-my-suffix.model';
import { IAppCartMySuffix } from 'app/shared/model/app-cart-my-suffix.model';

export interface IAppUserMySuffix {
  id?: number;
  name?: string | null;
  gender?: string | null;
  dob?: string | null;
  status?: string | null;
  orders?: IAppOrderMySuffix[] | null;
  posts?: IAppPostMySuffix[] | null;
  courses?: IAppCourseMySuffix[] | null;
  availableCourses?: IAppAvailableCourseMySuffix[] | null;
  cart?: IAppCartMySuffix | null;
}

export const defaultValue: Readonly<IAppUserMySuffix> = {};
