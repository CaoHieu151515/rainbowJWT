import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppMultipleChoiceAnswerMySuffix from './app-multiple-choice-answer-my-suffix';
import AppMultipleChoiceAnswerMySuffixDetail from './app-multiple-choice-answer-my-suffix-detail';
import AppMultipleChoiceAnswerMySuffixUpdate from './app-multiple-choice-answer-my-suffix-update';
import AppMultipleChoiceAnswerMySuffixDeleteDialog from './app-multiple-choice-answer-my-suffix-delete-dialog';

const AppMultipleChoiceAnswerMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppMultipleChoiceAnswerMySuffix />} />
    <Route path="new" element={<AppMultipleChoiceAnswerMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppMultipleChoiceAnswerMySuffixDetail />} />
      <Route path="edit" element={<AppMultipleChoiceAnswerMySuffixUpdate />} />
      <Route path="delete" element={<AppMultipleChoiceAnswerMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppMultipleChoiceAnswerMySuffixRoutes;
