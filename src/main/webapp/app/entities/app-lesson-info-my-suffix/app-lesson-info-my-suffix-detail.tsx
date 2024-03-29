import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-lesson-info-my-suffix.reducer';

export const AppLessonInfoMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appLessonInfoEntity = useAppSelector(state => state.appLessonInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appLessonInfoDetailsHeading">App Lesson Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appLessonInfoEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{appLessonInfoEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{appLessonInfoEntity.description}</dd>
          <dt>Lesson</dt>
          <dd>{appLessonInfoEntity.lesson ? appLessonInfoEntity.lesson.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-lesson-info-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-lesson-info-my-suffix/${appLessonInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppLessonInfoMySuffixDetail;
