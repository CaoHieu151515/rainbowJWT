import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppLessonPDFMySuffix } from 'app/shared/model/app-lesson-pdf-my-suffix.model';
import { getEntities } from './app-lesson-pdf-my-suffix.reducer';

export const AppLessonPDFMySuffix = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const appLessonPDFList = useAppSelector(state => state.appLessonPDF.entities);
  const loading = useAppSelector(state => state.appLessonPDF.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="app-lesson-pdf-my-suffix-heading" data-cy="AppLessonPDFHeading">
        App Lesson PDFS
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/app-lesson-pdf-my-suffix/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new App Lesson PDF
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appLessonPDFList && appLessonPDFList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Description</th>
                <th>Pdf Url</th>
                <th>Lesson</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appLessonPDFList.map((appLessonPDF, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/app-lesson-pdf-my-suffix/${appLessonPDF.id}`} color="link" size="sm">
                      {appLessonPDF.id}
                    </Button>
                  </td>
                  <td>{appLessonPDF.description}</td>
                  <td>{appLessonPDF.pdfUrl}</td>
                  <td>
                    {appLessonPDF.lesson ? (
                      <Link to={`/app-lesson-info-my-suffix/${appLessonPDF.lesson.id}`}>{appLessonPDF.lesson.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/app-lesson-pdf-my-suffix/${appLessonPDF.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-lesson-pdf-my-suffix/${appLessonPDF.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/app-lesson-pdf-my-suffix/${appLessonPDF.id}/delete`}
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
          !loading && <div className="alert alert-warning">No App Lesson PDFS found</div>
        )}
      </div>
    </div>
  );
};

export default AppLessonPDFMySuffix;
