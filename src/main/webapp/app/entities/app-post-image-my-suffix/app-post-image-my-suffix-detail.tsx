import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-post-image-my-suffix.reducer';

export const AppPostImageMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appPostImageEntity = useAppSelector(state => state.appPostImage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appPostImageDetailsHeading">App Post Image</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appPostImageEntity.id}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{appPostImageEntity.imageUrl}</dd>
          <dt>Post</dt>
          <dd>{appPostImageEntity.post ? appPostImageEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-post-image-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-post-image-my-suffix/${appPostImageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppPostImageMySuffixDetail;
