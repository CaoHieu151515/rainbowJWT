import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-lesson-pdf-my-suffix.reducer';

export const AppLessonPDFMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appLessonPDFEntity = useAppSelector(state => state.appLessonPDF.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appLessonPDFDetailsHeading">App Lesson PDF</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appLessonPDFEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{appLessonPDFEntity.description}</dd>
          <dt>
            <span id="pdfUrl">Pdf Url</span>
          </dt>
          <dd>{appLessonPDFEntity.pdfUrl}</dd>
          <dt>Lesson</dt>
          <dd>{appLessonPDFEntity.lesson ? appLessonPDFEntity.lesson.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-lesson-pdf-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-lesson-pdf-my-suffix/${appLessonPDFEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppLessonPDFMySuffixDetail;
