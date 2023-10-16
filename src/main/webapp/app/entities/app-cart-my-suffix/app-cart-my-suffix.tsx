import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppCartMySuffix } from 'app/shared/model/app-cart-my-suffix.model';
import { getEntities } from './app-cart-my-suffix.reducer';

export const AppCartMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appCartList = useAppSelector(state => state.appCart.entities);
  const loading = useAppSelector(state => state.appCart.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-cart-my-suffix-heading" data-cy="AppCartHeading">
        App Carts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-cart-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Cart
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appCartList && appCartList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Quantity</th>
                <th>User</th>
                <th>Products</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appCartList.map((appCart, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-cart-my-suffix/${appCart.id}`} color="link" size="sm">
                      {appCart.id}
                    </Button>
                  </td>
                  <td>{appCart.quantity}</td>
                  <td>{appCart.user ? <Link to={`/app-user-my-suffix/${appCart.user.id}`}>{appCart.user.id}</Link> : ''}</td>
                  <td>
                    {appCart.products
                      ? appCart.products.map((val, j) => (
                          <span key={j}>
                            <Link to={`/app-product-my-suffix/${val.id}`}>{val.id}</Link>
                            {j === appCart.products.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-cart-my-suffix/${appCart.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/app-cart-my-suffix/${appCart.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-cart-my-suffix/${appCart.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No App Carts found</div>
        )}
      </div>
    </div>
  );
};

export default AppCartMySuffix;
