import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-question-my-suffix.reducer';

export const AppQuestionMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appQuestionEntity = useAppSelector(state => state.appQuestion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appQuestionDetailsHeading">App Question</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appQuestionEntity.id}</dd>
          <dt>
            <span id="questionName">Question Name</span>
          </dt>
          <dd>{appQuestionEntity.questionName}</dd>
          <dt>
            <span id="questionText">Question Text</span>
          </dt>
          <dd>{appQuestionEntity.questionText}</dd>
          <dt>Lesson</dt>
          <dd>{appQuestionEntity.lesson ? appQuestionEntity.lesson.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-question-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-question-my-suffix/${appQuestionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppQuestionMySuffixDetail;
