import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-order-my-suffix.reducer';

export const AppOrderMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appOrderEntity = useAppSelector(state => state.appOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appOrderDetailsHeading">App Order</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appOrderEntity.id}</dd>
          <dt>
            <span id="total">Total</span>
          </dt>
          <dd>{appOrderEntity.total}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{appOrderEntity.createdAt ? <TextFormat value={appOrderEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{appOrderEntity.status}</dd>
          <dt>
            <span id="paymentID">Payment ID</span>
          </dt>
          <dd>{appOrderEntity.paymentID}</dd>
          <dt>User</dt>
          <dd>{appOrderEntity.user ? appOrderEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-order-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-order-my-suffix/${appOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppOrderMySuffixDetail;
