import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppPostMySuffix } from 'app/shared/model/app-post-my-suffix.model';
import { getEntities } from './app-post-my-suffix.reducer';

export const AppPostMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appPostList = useAppSelector(state => state.appPost.entities);
  const loading = useAppSelector(state => state.appPost.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-post-my-suffix-heading" data-cy="AppPostHeading">
        App Posts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-post-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Post
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appPostList && appPostList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Content</th>
                <th>Author</th>
                <th>Date Written</th>
                <th>Published Date</th>
                <th>Is Featured</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appPostList.map((appPost, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-post-my-suffix/${appPost.id}`} color="link" size="sm">
                      {appPost.id}
                    </Button>
                  </td>
                  <td>{appPost.title}</td>
                  <td>{appPost.content}</td>
                  <td>{appPost.author}</td>
                  <td>{appPost.dateWritten ? <TextFormat type="date" value={appPost.dateWritten} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {appPost.publishedDate ? <TextFormat type="date" value={appPost.publishedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{appPost.isFeatured ? 'true' : 'false'}</td>
                  <td>{appPost.user ? <Link to={`/app-user-my-suffix/${appPost.user.id}`}>{appPost.user.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-post-my-suffix/${appPost.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/app-post-my-suffix/${appPost.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-post-my-suffix/${appPost.id}/delete`}
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
          !loading && <div className="alert alert-warning">No App Posts found</div>
        )}
      </div>
    </div>
  );
};

export default AppPostMySuffix;
