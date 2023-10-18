import { IAppLessonInfoMySuffix } from 'app/shared/model/app-lesson-info-my-suffix.model';

export interface IAppLessonVideoMySuffix {
  id?: number;
  videoUrl?: string | null;
  description?: string | null;
  lessonInfo?: IAppLessonInfoMySuffix | null;
}

export const defaultValue: Readonly<IAppLessonVideoMySuffix> = {};
