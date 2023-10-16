import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-course-my-suffix.reducer';

export const AppCourseMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appCourseEntity = useAppSelector(state => state.appCourse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appCourseDetailsHeading">App Course</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appCourseEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{appCourseEntity.name}</dd>
          <dt>
            <span id="level">Level</span>
          </dt>
          <dd>{appCourseEntity.level}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>{appCourseEntity.image}</dd>
        </dl>
        <Button tag={Link} to="/app-course-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-course-my-suffix/${appCourseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppCourseMySuffixDetail;
