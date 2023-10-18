import { IAppMultipleChoiceAnswerMySuffix } from 'app/shared/model/app-multiple-choice-answer-my-suffix.model';
import { IAppQuestionVideoInfoMySuffix } from 'app/shared/model/app-question-video-info-my-suffix.model';
import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';

export interface IAppQuestionMySuffix {
  id?: number;
  questionName?: string | null;
  questionText?: string | null;
  questions?: IAppMultipleChoiceAnswerMySuffix[] | null;
  appQuestion?: IAppQuestionVideoInfoMySuffix | null;
  lesson?: IAppLessonMySuffix | null;
}

export const defaultValue: Readonly<IAppQuestionMySuffix> = {};
