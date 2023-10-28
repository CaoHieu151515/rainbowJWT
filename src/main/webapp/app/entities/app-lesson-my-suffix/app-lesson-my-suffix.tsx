import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';
import { getEntities } from './app-lesson-my-suffix.reducer';

export const AppLessonMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appLessonList = useAppSelector(state => state.appLesson.entities);
  const loading = useAppSelector(state => state.appLesson.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-lesson-my-suffix-heading" data-cy="AppLessonHeading">
        App Lessons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-lesson-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Lesson
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appLessonList && appLessonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Video Url</th>
                <th>Course</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appLessonList.map((appLesson, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-lesson-my-suffix/${appLesson.id}`} color="link" size="sm">
                      {appLesson.id}
                    </Button>
                  </td>
                  <td>{appLesson.videoUrl}</td>
                  <td>{appLesson.course ? <Link to={`/app-course-my-suffix/${appLesson.course.id}`}>{appLesson.course.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/app-lesson-my-suffix/${appLesson.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-lesson-my-suffix/${appLesson.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-lesson-my-suffix/${appLesson.id}/delete`}
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
          !loading && <div className="alert alert-warning">No App Lessons found</div>
        )}
      </div>
    </div>
  );
};

export default AppLessonMySuffix;
