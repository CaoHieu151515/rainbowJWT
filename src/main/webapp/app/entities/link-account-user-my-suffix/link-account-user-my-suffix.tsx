import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILinkAccountUserMySuffix } from 'app/shared/model/link-account-user-my-suffix.model';
import { getEntities } from './link-account-user-my-suffix.reducer';

export const LinkAccountUserMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const linkAccountUserList = useAppSelector(state => state.linkAccountUser.entities);
  const loading = useAppSelector(state => state.linkAccountUser.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="link-account-user-my-suffix-heading" data-cy="LinkAccountUserHeading">
        Link Account Users
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/link-account-user-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Link Account User
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {linkAccountUserList && linkAccountUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>User</th>
                <th>App User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {linkAccountUserList.map((linkAccountUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/link-account-user-my-suffix/${linkAccountUser.id}`} color="link" size="sm">
                      {linkAccountUser.id}
                    </Button>
                  </td>
                  <td>{linkAccountUser.user ? linkAccountUser.user.id : ''}</td>
                  <td>
                    {linkAccountUser.appUser ? (
                      <Link to={`/app-user-my-suffix/${linkAccountUser.appUser.id}`}>{linkAccountUser.appUser.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/link-account-user-my-suffix/${linkAccountUser.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/link-account-user-my-suffix/${linkAccountUser.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/link-account-user-my-suffix/${linkAccountUser.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Link Account Users found</div>
        )}
      </div>
    </div>
  );
};

export default LinkAccountUserMySuffix;
