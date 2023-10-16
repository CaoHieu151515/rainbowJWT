import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppProductMySuffix } from 'app/shared/model/app-product-my-suffix.model';
import { getEntities as getAppProducts } from 'app/entities/app-product-my-suffix/app-product-my-suffix.reducer';
import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';
import { getEntities as getAppOrders } from 'app/entities/app-order-my-suffix/app-order-my-suffix.reducer';
import { IAppOrderItemMySuffix } from 'app/shared/model/app-order-item-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-order-item-my-suffix.reducer';

export const AppOrderItemMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appProducts = useAppSelector(state => state.appProduct.entities);
  const appOrders = useAppSelector(state => state.appOrder.entities);
  const appOrderItemEntity = useAppSelector(state => state.appOrderItem.entity);
  const loading = useAppSelector(state => state.appOrderItem.loading);
  const updating = useAppSelector(state => state.appOrderItem.updating);
  const updateSuccess = useAppSelector(state => state.appOrderItem.updateSuccess);

  const handleClose = () => {
    navigate('/app-order-item-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppProducts({}));
    dispatch(getAppOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appOrderItemEntity,
      ...values,
      product: appProducts.find(it => it.id.toString() === values.product.toString()),
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
          ...appOrderItemEntity,
          product: appOrderItemEntity?.product?.id,
          order: appOrderItemEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appOrderItem.home.createOrEditLabel" data-cy="AppOrderItemCreateUpdateHeading">
            Create or edit a App Order Item
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
                <ValidatedField name="id" required readOnly id="app-order-item-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Quantity" id="app-order-item-my-suffix-quantity" name="quantity" data-cy="quantity" type="text" />
              <ValidatedField label="Price" id="app-order-item-my-suffix-price" name="price" data-cy="price" type="text" />
              <ValidatedField label="Unit" id="app-order-item-my-suffix-unit" name="unit" data-cy="unit" type="text" />
              <ValidatedField label="Note" id="app-order-item-my-suffix-note" name="note" data-cy="note" type="text" />
              <ValidatedField id="app-order-item-my-suffix-product" name="product" data-cy="product" label="Product" type="select">
                <option value="" key="0" />
                {appProducts
                  ? appProducts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="app-order-item-my-suffix-order" name="order" data-cy="order" label="Order" type="select">
                <option value="" key="0" />
                {appOrders
                  ? appOrders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-order-item-my-suffix" replace color="info">
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

export default AppOrderItemMySuffixUpdate;
