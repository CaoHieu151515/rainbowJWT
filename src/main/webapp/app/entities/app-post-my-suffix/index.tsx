import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppPostMySuffix from './app-post-my-suffix';
import AppPostMySuffixDetail from './app-post-my-suffix-detail';
import AppPostMySuffixUpdate from './app-post-my-suffix-update';
import AppPostMySuffixDeleteDialog from './app-post-my-suffix-delete-dialog';

const AppPostMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppPostMySuffix />} />
    <Route path="new" element={<AppPostMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppPostMySuffixDetail />} />
      <Route path="edit" element={<AppPostMySuffixUpdate />} />
      <Route path="delete" element={<AppPostMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppPostMySuffixRoutes;
