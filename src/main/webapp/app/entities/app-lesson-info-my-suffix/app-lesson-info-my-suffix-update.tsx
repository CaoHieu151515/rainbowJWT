import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';
import { getEntities as getAppLessons } from 'app/entities/app-lesson-my-suffix/app-lesson-my-suffix.reducer';
import { IAppLessonInfoMySuffix } from 'app/shared/model/app-lesson-info-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-lesson-info-my-suffix.reducer';

export const AppLessonInfoMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appLessons = useAppSelector(state => state.appLesson.entities);
  const appLessonInfoEntity = useAppSelector(state => state.appLessonInfo.entity);
  const loading = useAppSelector(state => state.appLessonInfo.loading);
  const updating = useAppSelector(state => state.appLessonInfo.updating);
  const updateSuccess = useAppSelector(state => state.appLessonInfo.updateSuccess);

  const handleClose = () => {
    navigate('/app-lesson-info-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppLessons({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appLessonInfoEntity,
      ...values,
      lesson: appLessons.find(it => it.id.toString() === values.lesson.toString()),
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
          ...appLessonInfoEntity,
          lesson: appLessonInfoEntity?.lesson?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appLessonInfo.home.createOrEditLabel" data-cy="AppLessonInfoCreateUpdateHeading">
            Create or edit a App Lesson Info
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
                <ValidatedField name="id" required readOnly id="app-lesson-info-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Description"
                id="app-lesson-info-my-suffix-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label="Pdf Url" id="app-lesson-info-my-suffix-pdfUrl" name="pdfUrl" data-cy="pdfUrl" type="text" />
              <ValidatedField id="app-lesson-info-my-suffix-lesson" name="lesson" data-cy="lesson" label="Lesson" type="select">
                <option value="" key="0" />
                {appLessons
                  ? appLessons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-lesson-info-my-suffix" replace color="info">
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

export default AppLessonInfoMySuffixUpdate;
