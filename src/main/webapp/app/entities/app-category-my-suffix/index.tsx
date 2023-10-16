import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppCategoryMySuffix from './app-category-my-suffix';
import AppCategoryMySuffixDetail from './app-category-my-suffix-detail';
import AppCategoryMySuffixUpdate from './app-category-my-suffix-update';
import AppCategoryMySuffixDeleteDialog from './app-category-my-suffix-delete-dialog';

const AppCategoryMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AppCategoryMySuffix />} />
    <Route path="new" element={<AppCategoryMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<AppCategoryMySuffixDetail />} />
      <Route path="edit" element={<AppCategoryMySuffixUpdate />} />
      <Route path="delete" element={<AppCategoryMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppCategoryMySuffixRoutes;
