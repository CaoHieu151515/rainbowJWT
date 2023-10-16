import dayjs from 'dayjs';
import { IAppPostImageMySuffix } from 'app/shared/model/app-post-image-my-suffix.model';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';

export interface IAppPostMySuffix {
  id?: number;
  title?: string | null;
  content?: string | null;
  author?: string | null;
  dateWritten?: string | null;
  publishedDate?: string | null;
  isFeatured?: boolean | null;
  confirm?: boolean | null;
  images?: IAppPostImageMySuffix[] | null;
  user?: IAppUserMySuffix | null;
}

export const defaultValue: Readonly<IAppPostMySuffix> = {
  isFeatured: false,
  confirm: false,
};
