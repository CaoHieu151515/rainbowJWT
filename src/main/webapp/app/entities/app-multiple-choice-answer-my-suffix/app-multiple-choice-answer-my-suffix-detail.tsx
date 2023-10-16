import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-multiple-choice-answer-my-suffix.reducer';

export const AppMultipleChoiceAnswerMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appMultipleChoiceAnswerEntity = useAppSelector(state => state.appMultipleChoiceAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appMultipleChoiceAnswerDetailsHeading">App Multiple Choice Answer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appMultipleChoiceAnswerEntity.id}</dd>
          <dt>
            <span id="answerText">Answer Text</span>
          </dt>
          <dd>{appMultipleChoiceAnswerEntity.answerText}</dd>
          <dt>
            <span id="isCorrect">Is Correct</span>
          </dt>
          <dd>{appMultipleChoiceAnswerEntity.isCorrect ? 'true' : 'false'}</dd>
          <dt>Question</dt>
          <dd>{appMultipleChoiceAnswerEntity.question ? appMultipleChoiceAnswerEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-multiple-choice-answer-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-multiple-choice-answer-my-suffix/${appMultipleChoiceAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppMultipleChoiceAnswerMySuffixDetail;
