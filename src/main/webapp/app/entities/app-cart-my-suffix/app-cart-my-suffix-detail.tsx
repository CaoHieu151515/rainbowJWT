import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './app-cart-my-suffix.reducer';

export const AppCartMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const appCartEntity = useAppSelector(state => state.appCart.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appCartDetailsHeading">App Cart</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{appCartEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{appCartEntity.quantity}</dd>
          <dt>User</dt>
          <dd>{appCartEntity.user ? appCartEntity.user.id : ''}</dd>
          <dt>Products</dt>
          <dd>
            {appCartEntity.products
              ? appCartEntity.products.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {appCartEntity.products && i === appCartEntity.products.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/app-cart-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-cart-my-suffix/${appCartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppCartMySuffixDetail;
