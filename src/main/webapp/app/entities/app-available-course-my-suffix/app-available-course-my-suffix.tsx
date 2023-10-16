import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppAvailableCourseMySuffix } from 'app/shared/model/app-available-course-my-suffix.model';
import { getEntities } from './app-available-course-my-suffix.reducer';

export const AppAvailableCourseMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appAvailableCourseList = useAppSelector(state => state.appAvailableCourse.entities);
  const loading = useAppSelector(state => state.appAvailableCourse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-available-course-my-suffix-heading" data-cy="AppAvailableCourseHeading">
        App Available Courses
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-available-course-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Available Course
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appAvailableCourseList && appAvailableCourseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Courses</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appAvailableCourseList.map((appAvailableCourse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-available-course-my-suffix/${appAvailableCourse.id}`} color="link" size="sm">
                      {appAvailableCourse.id}
                    </Button>
                  </td>
                  <td>
                    {appAvailableCourse.courses ? (
                      <Link to={`/app-course-my-suffix/${appAvailableCourse.courses.id}`}>{appAvailableCourse.courses.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/app-available-course-my-suffix/${appAvailableCourse.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-available-course-my-suffix/${appAvailableCourse.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-available-course-my-suffix/${appAvailableCourse.id}/delete`}
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
          !loading && <div className="alert alert-warning">No App Available Courses found</div>
        )}
      </div>
    </div>
  );
};

export default AppAvailableCourseMySuffix;
