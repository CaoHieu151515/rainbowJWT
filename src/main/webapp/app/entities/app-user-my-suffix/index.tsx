import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppUserMySuffix from './app-user-my-suffix';
import AppUserMySuffixDetail from './app-user-my-suffix-detail';
import AppUserMySuffixUpdate from './app-user-my-suffix-update';
import AppUserMySuffixDeleteDialog from './app-user-my-suffix-delete-dialog';

const AppUserMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppUserMySuffix />} />
    <Route path="new" element={<AppUserMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppUserMySuffixDetail />} />
      <Route path="edit" element={<AppUserMySuffixUpdate />} />
      <Route path="delete" element={<AppUserMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppUserMySuffixRoutes;
