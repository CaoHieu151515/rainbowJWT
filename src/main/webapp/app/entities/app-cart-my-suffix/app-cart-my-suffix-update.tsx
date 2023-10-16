import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { getEntities as getAppUsers } from 'app/entities/app-user-my-suffix/app-user-my-suffix.reducer';
import { IAppProductMySuffix } from 'app/shared/model/app-product-my-suffix.model';
import { getEntities as getAppProducts } from 'app/entities/app-product-my-suffix/app-product-my-suffix.reducer';
import { IAppCartMySuffix } from 'app/shared/model/app-cart-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-cart-my-suffix.reducer';

export const AppCartMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUsers = useAppSelector(state => state.appUser.entities);
  const appProducts = useAppSelector(state => state.appProduct.entities);
  const appCartEntity = useAppSelector(state => state.appCart.entity);
  const loading = useAppSelector(state => state.appCart.loading);
  const updating = useAppSelector(state => state.appCart.updating);
  const updateSuccess = useAppSelector(state => state.appCart.updateSuccess);

  const handleClose = () => {
    navigate('/app-cart-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppUsers({}));
    dispatch(getAppProducts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appCartEntity,
      ...values,
      products: mapIdList(values.products),
      user: appUsers.find(it => it.id.toString() === values.user.toString()),
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
          ...appCartEntity,
          user: appCartEntity?.user?.id,
          products: appCartEntity?.products?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appCart.home.createOrEditLabel" data-cy="AppCartCreateUpdateHeading">
            Create or edit a App Cart
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
                <ValidatedField name="id" required readOnly id="app-cart-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Quantity" id="app-cart-my-suffix-quantity" name="quantity" data-cy="quantity" type="text" />
              <ValidatedField id="app-cart-my-suffix-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {appUsers
                  ? appUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Products" id="app-cart-my-suffix-products" data-cy="products" type="select" multiple name="products">
                <option value="" key="0" />
                {appProducts
                  ? appProducts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-cart-my-suffix" replace color="info">
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

export default AppCartMySuffixUpdate;
