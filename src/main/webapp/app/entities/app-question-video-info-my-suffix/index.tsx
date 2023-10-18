import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppQuestionVideoInfoMySuffix from './app-question-video-info-my-suffix';
import AppQuestionVideoInfoMySuffixDetail from './app-question-video-info-my-suffix-detail';
import AppQuestionVideoInfoMySuffixUpdate from './app-question-video-info-my-suffix-update';
import AppQuestionVideoInfoMySuffixDeleteDialog from './app-question-video-info-my-suffix-delete-dialog';

const AppQuestionVideoInfoMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppQuestionVideoInfoMySuffix />} />
    <Route path="new" element={<AppQuestionVideoInfoMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppQuestionVideoInfoMySuffixDetail />} />
      <Route path="edit" element={<AppQuestionVideoInfoMySuffixUpdate />} />
      <Route path="delete" element={<AppQuestionVideoInfoMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppQuestionVideoInfoMySuffixRoutes;
