import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LinkAccountUserMySuffix from './link-account-user-my-suffix';
import LinkAccountUserMySuffixDetail from './link-account-user-my-suffix-detail';
import LinkAccountUserMySuffixUpdate from './link-account-user-my-suffix-update';
import LinkAccountUserMySuffixDeleteDialog from './link-account-user-my-suffix-delete-dialog';

const LinkAccountUserMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LinkAccountUserMySuffix />} />
    <Route path="new" element={<LinkAccountUserMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<LinkAccountUserMySuffixDetail />} />
      <Route path="edit" element={<LinkAccountUserMySuffixUpdate />} />
      <Route path="delete" element={<LinkAccountUserMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LinkAccountUserMySuffixRoutes;
