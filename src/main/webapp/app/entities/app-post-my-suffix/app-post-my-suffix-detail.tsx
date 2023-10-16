import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-post-my-suffix.reducer';

export const AppPostMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appPostEntity = useAppSelector(state => state.appPost.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appPostDetailsHeading">App Post</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appPostEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{appPostEntity.title}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{appPostEntity.content}</dd>
          <dt>
            <span id="author">Author</span>
          </dt>
          <dd>{appPostEntity.author}</dd>
          <dt>
            <span id="dateWritten">Date Written</span>
          </dt>
          <dd>
            {appPostEntity.dateWritten ? <TextFormat value={appPostEntity.dateWritten} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="publishedDate">Published Date</span>
          </dt>
          <dd>
            {appPostEntity.publishedDate ? <TextFormat value={appPostEntity.publishedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isFeatured">Is Featured</span>
          </dt>
          <dd>{appPostEntity.isFeatured ? 'true' : 'false'}</dd>
          <dt>
            <span id="confirm">Confirm</span>
          </dt>
          <dd>{appPostEntity.confirm ? 'true' : 'false'}</dd>
          <dt>User</dt>
          <dd>{appPostEntity.user ? appPostEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-post-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-post-my-suffix/${appPostEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppPostMySuffixDetail;
