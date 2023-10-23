import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-product-my-suffix.reducer';

export const AppProductMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appProductEntity = useAppSelector(state => state.appProduct.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appProductDetailsHeading">App Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appProductEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{appProductEntity.name}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{appProductEntity.price}</dd>
          <dt>
            <span id="unit">Unit</span>
          </dt>
          <dd>{appProductEntity.unit}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{appProductEntity.description}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{appProductEntity.status}</dd>
          <dt>
            <span id="courseId">Course Id</span>
          </dt>
          <dd>{appProductEntity.courseId}</dd>
          <dt>Category</dt>
          <dd>{appProductEntity.category ? appProductEntity.category.id : ''}</dd>
          <dt>Images</dt>
          <dd>{appProductEntity.images ? appProductEntity.images.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-product-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-product-my-suffix/${appProductEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppProductMySuffixDetail;
