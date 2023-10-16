import { IAppLessonVideoMySuffix } from 'app/shared/model/app-lesson-video-my-suffix.model';
import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';

export interface IAppLessonInfoMySuffix {
  id?: number;
  description?: string | null;
  pdfUrl?: string | null;
  videos?: IAppLessonVideoMySuffix[] | null;
  lesson?: IAppLessonMySuffix | null;
}

export const defaultValue: Readonly<IAppLessonInfoMySuffix> = {};
