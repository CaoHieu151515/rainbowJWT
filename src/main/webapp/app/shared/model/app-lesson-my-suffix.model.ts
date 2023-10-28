import { IAppQuestionMySuffix } from 'app/shared/model/app-question-my-suffix.model';
import { IAppLessonInfoMySuffix } from 'app/shared/model/app-lesson-info-my-suffix.model';
import { IAppCourseMySuffix } from 'app/shared/model/app-course-my-suffix.model';

export interface IAppLessonMySuffix {
  id?: number;
  videoUrl?: string | null;
  lessons?: IAppQuestionMySuffix[] | null;
  lessonInfos?: IAppLessonInfoMySuffix[] | null;
  course?: IAppCourseMySuffix | null;
}

export const defaultValue: Readonly<IAppLessonMySuffix> = {};
