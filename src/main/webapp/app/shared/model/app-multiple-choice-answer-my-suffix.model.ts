import { IAppQuestionMySuffix } from 'app/shared/model/app-question-my-suffix.model';

export interface IAppMultipleChoiceAnswerMySuffix {
  id?: number;
  answerText?: string | null;
  isCorrect?: boolean | null;
  question?: IAppQuestionMySuffix | null;
}

export const defaultValue: Readonly<IAppMultipleChoiceAnswerMySuffix> = {
  isCorrect: false,
};
