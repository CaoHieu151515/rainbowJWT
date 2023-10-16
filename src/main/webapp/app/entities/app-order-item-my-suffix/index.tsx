import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppOrderItemMySuffix from './app-order-item-my-suffix';
import AppOrderItemMySuffixDetail from './app-order-item-my-suffix-detail';
import AppOrderItemMySuffixUpdate from './app-order-item-my-suffix-update';
import AppOrderItemMySuffixDeleteDialog from './app-order-item-my-suffix-delete-dialog';

const AppOrderItemMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppOrderItemMySuffix />} />
    <Route path="new" element={<AppOrderItemMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppOrderItemMySuffixDetail />} />
      <Route path="edit" element={<AppOrderItemMySuffixUpdate />} />
      <Route path="delete" element={<AppOrderItemMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppOrderItemMySuffixRoutes;
