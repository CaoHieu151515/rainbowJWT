import { IAppLessonInfoMySuffix } from 'app/shared/model/app-lesson-info-my-suffix.model';

export interface IAppLessonPDFMySuffix {
  id?: number;
  description?: string | null;
  pdfUrl?: string | null;
  lesson?: IAppLessonInfoMySuffix | null;
}

export const defaultValue: Readonly<IAppLessonPDFMySuffix> = {};
