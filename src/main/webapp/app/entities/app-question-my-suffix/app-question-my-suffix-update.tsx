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
import { IAppQuestionMySuffix } from 'app/shared/model/app-question-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-question-my-suffix.reducer';

export const AppQuestionMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appLessons = useAppSelector(state => state.appLesson.entities);
  const appQuestionEntity = useAppSelector(state => state.appQuestion.entity);
  const loading = useAppSelector(state => state.appQuestion.loading);
  const updating = useAppSelector(state => state.appQuestion.updating);
  const updateSuccess = useAppSelector(state => state.appQuestion.updateSuccess);

  const handleClose = () => {
    navigate('/app-question-my-suffix');
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
      ...appQuestionEntity,
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
          ...appQuestionEntity,
          lesson: appQuestionEntity?.lesson?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appQuestion.home.createOrEditLabel" data-cy="AppQuestionCreateUpdateHeading">
            Create or edit a App Question
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
                <ValidatedField name="id" required readOnly id="app-question-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Question Name"
                id="app-question-my-suffix-questionName"
                name="questionName"
                data-cy="questionName"
                type="text"
              />
              <ValidatedField
                label="Question Text"
                id="app-question-my-suffix-questionText"
                name="questionText"
                data-cy="questionText"
                type="text"
              />
              <ValidatedField id="app-question-my-suffix-lesson" name="lesson" data-cy="lesson" label="Lesson" type="select">
                <option value="" key="0" />
                {appLessons
                  ? appLessons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-question-my-suffix" replace color="info">
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

export default AppQuestionMySuffixUpdate;
