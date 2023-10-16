import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-product-image-my-suffix.reducer';

export const AppProductImageMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appProductImageEntity = useAppSelector(state => state.appProductImage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appProductImageDetailsHeading">App Product Image</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appProductImageEntity.id}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{appProductImageEntity.imageUrl}</dd>
        </dl>
        <Button tag={Link} to="/app-product-image-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-product-image-my-suffix/${appProductImageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppProductImageMySuffixDetail;
