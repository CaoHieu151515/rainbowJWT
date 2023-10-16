import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-order-item-my-suffix.reducer';

export const AppOrderItemMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appOrderItemEntity = useAppSelector(state => state.appOrderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appOrderItemDetailsHeading">App Order Item</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appOrderItemEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{appOrderItemEntity.quantity}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{appOrderItemEntity.price}</dd>
          <dt>
            <span id="unit">Unit</span>
          </dt>
          <dd>{appOrderItemEntity.unit}</dd>
          <dt>
            <span id="note">Note</span>
          </dt>
          <dd>{appOrderItemEntity.note}</dd>
          <dt>Product</dt>
          <dd>{appOrderItemEntity.product ? appOrderItemEntity.product.id : ''}</dd>
          <dt>Order</dt>
          <dd>{appOrderItemEntity.order ? appOrderItemEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-order-item-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-order-item-my-suffix/${appOrderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppOrderItemMySuffixDetail;
