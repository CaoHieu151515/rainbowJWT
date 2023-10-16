import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';
import { getEntities as getAppOrders } from 'app/entities/app-order-my-suffix/app-order-my-suffix.reducer';
import { IAppPaymentMySuffix } from 'app/shared/model/app-payment-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-payment-my-suffix.reducer';

export const AppPaymentMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appOrders = useAppSelector(state => state.appOrder.entities);
  const appPaymentEntity = useAppSelector(state => state.appPayment.entity);
  const loading = useAppSelector(state => state.appPayment.loading);
  const updating = useAppSelector(state => state.appPayment.updating);
  const updateSuccess = useAppSelector(state => state.appPayment.updateSuccess);

  const handleClose = () => {
    navigate('/app-payment-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appPaymentEntity,
      ...values,
      order: appOrders.find(it => it.id.toString() === values.order.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...appPaymentEntity,
          order: appPaymentEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appPayment.home.createOrEditLabel" data-cy="AppPaymentCreateUpdateHeading">
            Create or edit a App Payment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="app-payment-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Method" id="app-payment-my-suffix-method" name="method" data-cy="method" type="text" />
              <ValidatedField label="Status" id="app-payment-my-suffix-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Amount" id="app-payment-my-suffix-amount" name="amount" data-cy="amount" type="text" />
              <ValidatedField label="Note" id="app-payment-my-suffix-note" name="note" data-cy="note" type="text" />
              <ValidatedField id="app-payment-my-suffix-order" name="order" data-cy="order" label="Order" type="select">
                <option value="" key="0" />
                {appOrders
                  ? appOrders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-payment-my-suffix" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AppPaymentMySuffixUpdate;
