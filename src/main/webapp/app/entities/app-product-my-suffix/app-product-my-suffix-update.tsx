import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppCategoryMySuffix } from 'app/shared/model/app-category-my-suffix.model';
import { getEntities as getAppCategories } from 'app/entities/app-category-my-suffix/app-category-my-suffix.reducer';
import { IAppProductImageMySuffix } from 'app/shared/model/app-product-image-my-suffix.model';
import { getEntities as getAppProductImages } from 'app/entities/app-product-image-my-suffix/app-product-image-my-suffix.reducer';
import { IAppCartMySuffix } from 'app/shared/model/app-cart-my-suffix.model';
import { getEntities as getAppCarts } from 'app/entities/app-cart-my-suffix/app-cart-my-suffix.reducer';
import { IAppProductMySuffix } from 'app/shared/model/app-product-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-product-my-suffix.reducer';

export const AppProductMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appCategories = useAppSelector(state => state.appCategory.entities);
  const appProductImages = useAppSelector(state => state.appProductImage.entities);
  const appCarts = useAppSelector(state => state.appCart.entities);
  const appProductEntity = useAppSelector(state => state.appProduct.entity);
  const loading = useAppSelector(state => state.appProduct.loading);
  const updating = useAppSelector(state => state.appProduct.updating);
  const updateSuccess = useAppSelector(state => state.appProduct.updateSuccess);

  const handleClose = () => {
    navigate('/app-product-my-suffix');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAppCategories({}));
    dispatch(getAppProductImages({}));
    dispatch(getAppCarts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appProductEntity,
      ...values,
      category: appCategories.find(it => it.id.toString() === values.category.toString()),
      images: appProductImages.find(it => it.id.toString() === values.images.toString()),
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
          ...appProductEntity,
          category: appProductEntity?.category?.id,
          images: appProductEntity?.images?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appProduct.home.createOrEditLabel" data-cy="AppProductCreateUpdateHeading">
            Create or edit a App Product
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
                <ValidatedField name="id" required readOnly id="app-product-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Name" id="app-product-my-suffix-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Price" id="app-product-my-suffix-price" name="price" data-cy="price" type="text" />
              <ValidatedField label="Unit" id="app-product-my-suffix-unit" name="unit" data-cy="unit" type="text" />
              <ValidatedField
                label="Description"
                id="app-product-my-suffix-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label="Status" id="app-product-my-suffix-status" name="status" data-cy="status" type="text" />
              <ValidatedField id="app-product-my-suffix-category" name="category" data-cy="category" label="Category" type="select">
                <option value="" key="0" />
                {appCategories
                  ? appCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="app-product-my-suffix-images" name="images" data-cy="images" label="Images" type="select">
                <option value="" key="0" />
                {appProductImages
                  ? appProductImages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-product-my-suffix" replace color="info">
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

export default AppProductMySuffixUpdate;
