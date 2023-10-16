import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppPaymentMySuffix from './app-payment-my-suffix';
import AppPaymentMySuffixDetail from './app-payment-my-suffix-detail';
import AppPaymentMySuffixUpdate from './app-payment-my-suffix-update';
import AppPaymentMySuffixDeleteDialog from './app-payment-my-suffix-delete-dialog';

const AppPaymentMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppPaymentMySuffix />} />
    <Route path="new" element={<AppPaymentMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppPaymentMySuffixDetail />} />
      <Route path="edit" element={<AppPaymentMySuffixUpdate />} />
      <Route path="delete" element={<AppPaymentMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppPaymentMySuffixRoutes;
