import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppPostMySuffix } from 'app/shared/model/app-post-my-suffix.model';
import { getEntities as getAppPosts } from 'app/entities/app-post-my-suffix/app-post-my-suffix.reducer';
import { IAppPostImageMySuffix } from 'app/shared/model/app-post-image-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-post-image-my-suffix.reducer';

export const AppPostImageMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appPosts = useAppSelector(state => state.appPost.entities);
  const appPostImageEntity = useAppSelector(state => state.appPostImage.entity);
  const loading = useAppSelector(state => state.appPostImage.loading);
  const updating = useAppSelector(state => state.appPostImage.updating);
  const updateSuccess = useAppSelector(state => state.appPostImage.updateSuccess);

  const handleClose = () => {
    navigate('/app-post-image-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppPosts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appPostImageEntity,
      ...values,
      post: appPosts.find(it => it.id.toString() === values.post.toString()),
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
          ...appPostImageEntity,
          post: appPostImageEntity?.post?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appPostImage.home.createOrEditLabel" data-cy="AppPostImageCreateUpdateHeading">
            Create or edit a App Post Image
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
                <ValidatedField name="id" required readOnly id="app-post-image-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Image Url" id="app-post-image-my-suffix-imageUrl" name="imageUrl" data-cy="imageUrl" type="text" />
              <ValidatedField id="app-post-image-my-suffix-post" name="post" data-cy="post" label="Post" type="select">
                <option value="" key="0" />
                {appPosts
                  ? appPosts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-post-image-my-suffix" replace color="info">
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

export default AppPostImageMySuffixUpdate;
