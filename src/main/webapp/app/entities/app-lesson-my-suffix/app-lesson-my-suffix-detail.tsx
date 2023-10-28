import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-lesson-my-suffix.reducer';

export const AppLessonMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appLessonEntity = useAppSelector(state => state.appLesson.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appLessonDetailsHeading">App Lesson</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appLessonEntity.id}</dd>
          <dt>
            <span id="videoUrl">Video Url</span>
          </dt>
          <dd>{appLessonEntity.videoUrl}</dd>
          <dt>Course</dt>
          <dd>{appLessonEntity.course ? appLessonEntity.course.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-lesson-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-lesson-my-suffix/${appLessonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppLessonMySuffixDetail;
