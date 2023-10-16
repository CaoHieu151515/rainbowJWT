import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppLessonInfoMySuffix from './app-lesson-info-my-suffix';
import AppLessonInfoMySuffixDetail from './app-lesson-info-my-suffix-detail';
import AppLessonInfoMySuffixUpdate from './app-lesson-info-my-suffix-update';
import AppLessonInfoMySuffixDeleteDialog from './app-lesson-info-my-suffix-delete-dialog';

const AppLessonInfoMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppLessonInfoMySuffix />} />
    <Route path="new" element={<AppLessonInfoMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppLessonInfoMySuffixDetail />} />
      <Route path="edit" element={<AppLessonInfoMySuffixUpdate />} />
      <Route path="delete" element={<AppLessonInfoMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppLessonInfoMySuffixRoutes;
