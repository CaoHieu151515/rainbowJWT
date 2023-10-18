import { IAppLessonVideoMySuffix } from 'app/shared/model/app-lesson-video-my-suffix.model';
import { IAppLessonPDFMySuffix } from 'app/shared/model/app-lesson-pdf-my-suffix.model';
import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';

export interface IAppLessonInfoMySuffix {
  id?: number;
  name?: string | null;
  description?: string | null;
  videos?: IAppLessonVideoMySuffix[] | null;
  pdfs?: IAppLessonPDFMySuffix[] | null;
  lesson?: IAppLessonMySuffix | null;
}

export const defaultValue: Readonly<IAppLessonInfoMySuffix> = {};
