import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-payment-my-suffix.reducer';

export const AppPaymentMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appPaymentEntity = useAppSelector(state => state.appPayment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appPaymentDetailsHeading">App Payment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appPaymentEntity.id}</dd>
          <dt>
            <span id="method">Method</span>
          </dt>
          <dd>{appPaymentEntity.method}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{appPaymentEntity.status}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{appPaymentEntity.amount}</dd>
          <dt>
            <span id="note">Note</span>
          </dt>
          <dd>{appPaymentEntity.note}</dd>
          <dt>Order</dt>
          <dd>{appPaymentEntity.order ? appPaymentEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-payment-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-payment-my-suffix/${appPaymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppPaymentMySuffixDetail;
