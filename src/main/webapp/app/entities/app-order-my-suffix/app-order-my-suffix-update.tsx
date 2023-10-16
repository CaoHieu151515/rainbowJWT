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
import { IAppOrderMySuffix } from 'app/shared/model/app-order-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-order-my-suffix.reducer';

export const AppOrderMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUsers = useAppSelector(state => state.appUser.entities);
  const appOrderEntity = useAppSelector(state => state.appOrder.entity);
  const loading = useAppSelector(state => state.appOrder.loading);
  const updating = useAppSelector(state => state.appOrder.updating);
  const updateSuccess = useAppSelector(state => state.appOrder.updateSuccess);

  const handleClose = () => {
    navigate('/app-order-my-suffix');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAppUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...appOrderEntity,
      ...values,
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
      ? {
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...appOrderEntity,
          createdAt: convertDateTimeFromServer(appOrderEntity.createdAt),
          user: appOrderEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appOrder.home.createOrEditLabel" data-cy="AppOrderCreateUpdateHeading">
            Create or edit a App Order
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
                <ValidatedField name="id" required readOnly id="app-order-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Total" id="app-order-my-suffix-total" name="total" data-cy="total" type="text" />
              <ValidatedField
                label="Created At"
                id="app-order-my-suffix-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Status" id="app-order-my-suffix-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Payment ID" id="app-order-my-suffix-paymentID" name="paymentID" data-cy="paymentID" type="text" />
              <ValidatedField id="app-order-my-suffix-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {appUsers
                  ? appUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-order-my-suffix" replace color="info">
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

export default AppOrderMySuffixUpdate;
