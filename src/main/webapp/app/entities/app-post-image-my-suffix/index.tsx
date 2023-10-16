import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppPostImageMySuffix from './app-post-image-my-suffix';
import AppPostImageMySuffixDetail from './app-post-image-my-suffix-detail';
import AppPostImageMySuffixUpdate from './app-post-image-my-suffix-update';
import AppPostImageMySuffixDeleteDialog from './app-post-image-my-suffix-delete-dialog';

const AppPostImageMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppPostImageMySuffix />} />
    <Route path="new" element={<AppPostImageMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppPostImageMySuffixDetail />} />
      <Route path="edit" element={<AppPostImageMySuffixUpdate />} />
      <Route path="delete" element={<AppPostImageMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppPostImageMySuffixRoutes;
