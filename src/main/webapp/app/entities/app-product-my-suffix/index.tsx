import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppProductMySuffix from './app-product-my-suffix';
import AppProductMySuffixDetail from './app-product-my-suffix-detail';
import AppProductMySuffixUpdate from './app-product-my-suffix-update';
import AppProductMySuffixDeleteDialog from './app-product-my-suffix-delete-dialog';

const AppProductMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppProductMySuffix />} />
    <Route path="new" element={<AppProductMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppProductMySuffixDetail />} />
      <Route path="edit" element={<AppProductMySuffixUpdate />} />
      <Route path="delete" element={<AppProductMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppProductMySuffixRoutes;
