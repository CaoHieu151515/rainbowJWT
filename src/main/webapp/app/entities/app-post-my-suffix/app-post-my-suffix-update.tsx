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
import { IAppPostMySuffix } from 'app/shared/model/app-post-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-post-my-suffix.reducer';

export const AppPostMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appUsers = useAppSelector(state => state.appUser.entities);
  const appPostEntity = useAppSelector(state => state.appPost.entity);
  const loading = useAppSelector(state => state.appPost.loading);
  const updating = useAppSelector(state => state.appPost.updating);
  const updateSuccess = useAppSelector(state => state.appPost.updateSuccess);

  const handleClose = () => {
    navigate('/app-post-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
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
    values.dateWritten = convertDateTimeToServer(values.dateWritten);
    values.publishedDate = convertDateTimeToServer(values.publishedDate);

    const entity = {
      ...appPostEntity,
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
          dateWritten: displayDefaultDateTime(),
          publishedDate: displayDefaultDateTime(),
        }
      : {
          ...appPostEntity,
          dateWritten: convertDateTimeFromServer(appPostEntity.dateWritten),
          publishedDate: convertDateTimeFromServer(appPostEntity.publishedDate),
          user: appPostEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appPost.home.createOrEditLabel" data-cy="AppPostCreateUpdateHeading">
            Create or edit a App Post
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
                <ValidatedField name="id" required readOnly id="app-post-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Title" id="app-post-my-suffix-title" name="title" data-cy="title" type="text" />
              <ValidatedField label="Content" id="app-post-my-suffix-content" name="content" data-cy="content" type="text" />
              <ValidatedField label="Author" id="app-post-my-suffix-author" name="author" data-cy="author" type="text" />
              <ValidatedField
                label="Date Written"
                id="app-post-my-suffix-dateWritten"
                name="dateWritten"
                data-cy="dateWritten"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Published Date"
                id="app-post-my-suffix-publishedDate"
                name="publishedDate"
                data-cy="publishedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Is Featured"
                id="app-post-my-suffix-isFeatured"
                name="isFeatured"
                data-cy="isFeatured"
                check
                type="checkbox"
              />
              <ValidatedField id="app-post-my-suffix-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {appUsers
                  ? appUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-post-my-suffix" replace color="info">
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

export default AppPostMySuffixUpdate;
