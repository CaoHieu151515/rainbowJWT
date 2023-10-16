import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppQuestionMySuffix from './app-question-my-suffix';
import AppQuestionMySuffixDetail from './app-question-my-suffix-detail';
import AppQuestionMySuffixUpdate from './app-question-my-suffix-update';
import AppQuestionMySuffixDeleteDialog from './app-question-my-suffix-delete-dialog';

const AppQuestionMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppQuestionMySuffix />} />
    <Route path="new" element={<AppQuestionMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppQuestionMySuffixDetail />} />
      <Route path="edit" element={<AppQuestionMySuffixUpdate />} />
      <Route path="delete" element={<AppQuestionMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppQuestionMySuffixRoutes;
