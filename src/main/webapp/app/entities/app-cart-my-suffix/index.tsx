import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppCartMySuffix from './app-cart-my-suffix';
import AppCartMySuffixDetail from './app-cart-my-suffix-detail';
import AppCartMySuffixUpdate from './app-cart-my-suffix-update';
import AppCartMySuffixDeleteDialog from './app-cart-my-suffix-delete-dialog';

const AppCartMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppCartMySuffix />} />
    <Route path="new" element={<AppCartMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppCartMySuffixDetail />} />
      <Route path="edit" element={<AppCartMySuffixUpdate />} />
      <Route path="delete" element={<AppCartMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppCartMySuffixRoutes;
