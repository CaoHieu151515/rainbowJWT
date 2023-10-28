import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppCourseMySuffix } from 'app/shared/model/app-course-my-suffix.model';
import { getEntities as getAppCourses } from 'app/entities/app-course-my-suffix/app-course-my-suffix.reducer';
import { IAppLessonMySuffix } from 'app/shared/model/app-lesson-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-lesson-my-suffix.reducer';

export const AppLessonMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appCourses = useAppSelector(state => state.appCourse.entities);
  const appLessonEntity = useAppSelector(state => state.appLesson.entity);
  const loading = useAppSelector(state => state.appLesson.loading);
  const updating = useAppSelector(state => state.appLesson.updating);
  const updateSuccess = useAppSelector(state => state.appLesson.updateSuccess);

  const handleClose = () => {
    navigate('/app-lesson-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppCourses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appLessonEntity,
      ...values,
      course: appCourses.find(it => it.id.toString() === values.course.toString()),
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
          ...appLessonEntity,
          course: appLessonEntity?.course?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appLesson.home.createOrEditLabel" data-cy="AppLessonCreateUpdateHeading">
            Create or edit a App Lesson
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
                <ValidatedField name="id" required readOnly id="app-lesson-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Video Url" id="app-lesson-my-suffix-videoUrl" name="videoUrl" data-cy="videoUrl" type="text" />
              <ValidatedField id="app-lesson-my-suffix-course" name="course" data-cy="course" label="Course" type="select">
                <option value="" key="0" />
                {appCourses
                  ? appCourses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-lesson-my-suffix" replace color="info">
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

export default AppLessonMySuffixUpdate;
