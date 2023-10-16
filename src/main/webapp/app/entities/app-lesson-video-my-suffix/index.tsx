import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppLessonVideoMySuffix from './app-lesson-video-my-suffix';
import AppLessonVideoMySuffixDetail from './app-lesson-video-my-suffix-detail';
import AppLessonVideoMySuffixUpdate from './app-lesson-video-my-suffix-update';
import AppLessonVideoMySuffixDeleteDialog from './app-lesson-video-my-suffix-delete-dialog';

const AppLessonVideoMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppLessonVideoMySuffix />} />
    <Route path="new" element={<AppLessonVideoMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppLessonVideoMySuffixDetail />} />
      <Route path="edit" element={<AppLessonVideoMySuffixUpdate />} />
      <Route path="delete" element={<AppLessonVideoMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppLessonVideoMySuffixRoutes;
