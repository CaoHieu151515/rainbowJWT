import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppAvailableCourseMySuffix from './app-available-course-my-suffix';
import AppAvailableCourseMySuffixDetail from './app-available-course-my-suffix-detail';
import AppAvailableCourseMySuffixUpdate from './app-available-course-my-suffix-update';
import AppAvailableCourseMySuffixDeleteDialog from './app-available-course-my-suffix-delete-dialog';

const AppAvailableCourseMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppAvailableCourseMySuffix />} />
    <Route path="new" element={<AppAvailableCourseMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppAvailableCourseMySuffixDetail />} />
      <Route path="edit" element={<AppAvailableCourseMySuffixUpdate />} />
      <Route path="delete" element={<AppAvailableCourseMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppAvailableCourseMySuffixRoutes;
