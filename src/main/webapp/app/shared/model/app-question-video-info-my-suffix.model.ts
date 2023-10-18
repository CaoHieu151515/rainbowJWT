import { IAppQuestionMySuffix } from 'app/shared/model/app-question-my-suffix.model';

export interface IAppQuestionVideoInfoMySuffix {
  id?: number;
  description?: string | null;
  quenstionVideo?: string | null;
  appQuestion?: IAppQuestionMySuffix | null;
}

export const defaultValue: Readonly<IAppQuestionVideoInfoMySuffix> = {};
