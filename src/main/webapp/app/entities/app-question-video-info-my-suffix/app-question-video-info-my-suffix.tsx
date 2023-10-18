import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppQuestionVideoInfoMySuffix } from 'app/shared/model/app-question-video-info-my-suffix.model';
import { getEntities } from './app-question-video-info-my-suffix.reducer';

export const AppQuestionVideoInfoMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appQuestionVideoInfoList = useAppSelector(state => state.appQuestionVideoInfo.entities);
  const loading = useAppSelector(state => state.appQuestionVideoInfo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-question-video-info-my-suffix-heading" data-cy="AppQuestionVideoInfoHeading">
        App Question Video Infos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-question-video-info-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Question Video Info
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appQuestionVideoInfoList && appQuestionVideoInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Description</th>
                <th>Quenstion Video</th>
                <th>App Question</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appQuestionVideoInfoList.map((appQuestionVideoInfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-question-video-info-my-suffix/${appQuestionVideoInfo.id}`} color="link" size="sm">
                      {appQuestionVideoInfo.id}
                    </Button>
                  </td>
                  <td>{appQuestionVideoInfo.description}</td>
                  <td>{appQuestionVideoInfo.quenstionVideo}</td>
                  <td>
                    {appQuestionVideoInfo.appQuestion ? (
                      <Link to={`/app-question-my-suffix/${appQuestionVideoInfo.appQuestion.id}`}>
                        {appQuestionVideoInfo.appQuestion.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/app-question-video-info-my-suffix/${appQuestionVideoInfo.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-question-video-info-my-suffix/${appQuestionVideoInfo.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-question-video-info-my-suffix/${appQuestionVideoInfo.id}/delete`}
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
          !loading && <div className="alert alert-warning">No App Question Video Infos found</div>
        )}
      </div>
    </div>
  );
};

export default AppQuestionVideoInfoMySuffix;
