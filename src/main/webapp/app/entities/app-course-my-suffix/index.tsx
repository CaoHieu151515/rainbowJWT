import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppCourseMySuffix from './app-course-my-suffix';
import AppCourseMySuffixDetail from './app-course-my-suffix-detail';
import AppCourseMySuffixUpdate from './app-course-my-suffix-update';
import AppCourseMySuffixDeleteDialog from './app-course-my-suffix-delete-dialog';

const AppCourseMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppCourseMySuffix />} />
    <Route path="new" element={<AppCourseMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppCourseMySuffixDetail />} />
      <Route path="edit" element={<AppCourseMySuffixUpdate />} />
      <Route path="delete" element={<AppCourseMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppCourseMySuffixRoutes;
