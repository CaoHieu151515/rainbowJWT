import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-question-video-info-my-suffix.reducer';

export const AppQuestionVideoInfoMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appQuestionVideoInfoEntity = useAppSelector(state => state.appQuestionVideoInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appQuestionVideoInfoDetailsHeading">App Question Video Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appQuestionVideoInfoEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{appQuestionVideoInfoEntity.description}</dd>
          <dt>
            <span id="quenstionVideo">Quenstion Video</span>
          </dt>
          <dd>{appQuestionVideoInfoEntity.quenstionVideo}</dd>
          <dt>App Question</dt>
          <dd>{appQuestionVideoInfoEntity.appQuestion ? appQuestionVideoInfoEntity.appQuestion.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-question-video-info-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-question-video-info-my-suffix/${appQuestionVideoInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppQuestionVideoInfoMySuffixDetail;
