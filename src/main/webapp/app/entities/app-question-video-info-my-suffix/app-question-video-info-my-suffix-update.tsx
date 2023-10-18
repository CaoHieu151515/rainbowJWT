import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppQuestionMySuffix } from 'app/shared/model/app-question-my-suffix.model';
import { getEntities as getAppQuestions } from 'app/entities/app-question-my-suffix/app-question-my-suffix.reducer';
import { IAppQuestionVideoInfoMySuffix } from 'app/shared/model/app-question-video-info-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-question-video-info-my-suffix.reducer';

export const AppQuestionVideoInfoMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appQuestions = useAppSelector(state => state.appQuestion.entities);
  const appQuestionVideoInfoEntity = useAppSelector(state => state.appQuestionVideoInfo.entity);
  const loading = useAppSelector(state => state.appQuestionVideoInfo.loading);
  const updating = useAppSelector(state => state.appQuestionVideoInfo.updating);
  const updateSuccess = useAppSelector(state => state.appQuestionVideoInfo.updateSuccess);

  const handleClose = () => {
    navigate('/app-question-video-info-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppQuestions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appQuestionVideoInfoEntity,
      ...values,
      appQuestion: appQuestions.find(it => it.id.toString() === values.appQuestion.toString()),
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
          ...appQuestionVideoInfoEntity,
          appQuestion: appQuestionVideoInfoEntity?.appQuestion?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appQuestionVideoInfo.home.createOrEditLabel" data-cy="AppQuestionVideoInfoCreateUpdateHeading">
            Create or edit a App Question Video Info
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="app-question-video-info-my-suffix-id"
                  label="Id"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Description"
                id="app-question-video-info-my-suffix-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label="Quenstion Video"
                id="app-question-video-info-my-suffix-quenstionVideo"
                name="quenstionVideo"
                data-cy="quenstionVideo"
                type="text"
              />
              <ValidatedField
                id="app-question-video-info-my-suffix-appQuestion"
                name="appQuestion"
                data-cy="appQuestion"
                label="App Question"
                type="select"
              >
                <option value="" key="0" />
                {appQuestions
                  ? appQuestions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/app-question-video-info-my-suffix"
                replace
                color="info"
              >
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

export default AppQuestionVideoInfoMySuffixUpdate;
