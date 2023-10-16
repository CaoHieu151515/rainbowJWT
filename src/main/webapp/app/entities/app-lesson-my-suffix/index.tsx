import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppLessonMySuffix from './app-lesson-my-suffix';
import AppLessonMySuffixDetail from './app-lesson-my-suffix-detail';
import AppLessonMySuffixUpdate from './app-lesson-my-suffix-update';
import AppLessonMySuffixDeleteDialog from './app-lesson-my-suffix-delete-dialog';

const AppLessonMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppLessonMySuffix />} />
    <Route path="new" element={<AppLessonMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppLessonMySuffixDetail />} />
      <Route path="edit" element={<AppLessonMySuffixUpdate />} />
      <Route path="delete" element={<AppLessonMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppLessonMySuffixRoutes;
