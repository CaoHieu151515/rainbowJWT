import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppOrderMySuffix from './app-order-my-suffix';
import AppOrderMySuffixDetail from './app-order-my-suffix-detail';
import AppOrderMySuffixUpdate from './app-order-my-suffix-update';
import AppOrderMySuffixDeleteDialog from './app-order-my-suffix-delete-dialog';

const AppOrderMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppOrderMySuffix />} />
    <Route path="new" element={<AppOrderMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppOrderMySuffixDetail />} />
      <Route path="edit" element={<AppOrderMySuffixUpdate />} />
      <Route path="delete" element={<AppOrderMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppOrderMySuffixRoutes;
