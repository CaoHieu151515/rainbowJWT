import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppLessonPDFMySuffix from './app-lesson-pdf-my-suffix';
import AppLessonPDFMySuffixDetail from './app-lesson-pdf-my-suffix-detail';
import AppLessonPDFMySuffixUpdate from './app-lesson-pdf-my-suffix-update';
import AppLessonPDFMySuffixDeleteDialog from './app-lesson-pdf-my-suffix-delete-dialog';

const AppLessonPDFMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppLessonPDFMySuffix />} />
    <Route path="new" element={<AppLessonPDFMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppLessonPDFMySuffixDetail />} />
      <Route path="edit" element={<AppLessonPDFMySuffixUpdate />} />
      <Route path="delete" element={<AppLessonPDFMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppLessonPDFMySuffixRoutes;
