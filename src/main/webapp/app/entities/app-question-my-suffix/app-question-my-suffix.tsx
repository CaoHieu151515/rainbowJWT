import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppQuestionMySuffix } from 'app/shared/model/app-question-my-suffix.model';
import { getEntities } from './app-question-my-suffix.reducer';

export const AppQuestionMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appQuestionList = useAppSelector(state => state.appQuestion.entities);
  const loading = useAppSelector(state => state.appQuestion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-question-my-suffix-heading" data-cy="AppQuestionHeading">
        App Questions
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-question-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Question
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appQuestionList && appQuestionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Question Name</th>
                <th>Question Text</th>
                <th>Lesson</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appQuestionList.map((appQuestion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-question-my-suffix/${appQuestion.id}`} color="link" size="sm">
                      {appQuestion.id}
                    </Button>
                  </td>
                  <td>{appQuestion.questionName}</td>
                  <td>{appQuestion.questionText}</td>
                  <td>
                    {appQuestion.lesson ? <Link to={`/app-lesson-my-suffix/${appQuestion.lesson.id}`}>{appQuestion.lesson.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/app-question-my-suffix/${appQuestion.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-question-my-suffix/${appQuestion.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-question-my-suffix/${appQuestion.id}/delete`}
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
          !loading && <div className="alert alert-warning">No App Questions found</div>
        )}
      </div>
    </div>
  );
};

export default AppQuestionMySuffix;
