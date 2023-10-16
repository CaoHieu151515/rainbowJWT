import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppProductImageMySuffix from './app-product-image-my-suffix';
import AppProductImageMySuffixDetail from './app-product-image-my-suffix-detail';
import AppProductImageMySuffixUpdate from './app-product-image-my-suffix-update';
import AppProductImageMySuffixDeleteDialog from './app-product-image-my-suffix-delete-dialog';

const AppProductImageMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppProductImageMySuffix />} />
    <Route path="new" element={<AppProductImageMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppProductImageMySuffixDetail />} />
      <Route path="edit" element={<AppProductImageMySuffixUpdate />} />
      <Route path="delete" element={<AppProductImageMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppProductImageMySuffixRoutes;
