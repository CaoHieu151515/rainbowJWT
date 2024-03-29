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
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { getEntities as getAppUsers } from 'app/entities/app-user-my-suffix/app-user-my-suffix.reducer';
import { IAppAvailableCourseMySuffix } from 'app/shared/model/app-available-course-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-available-course-my-suffix.reducer';

export const AppAvailableCourseMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appCourses = useAppSelector(state => state.appCourse.entities);
  const appUsers = useAppSelector(state => state.appUser.entities);
  const appAvailableCourseEntity = useAppSelector(state => state.appAvailableCourse.entity);
  const loading = useAppSelector(state => state.appAvailableCourse.loading);
  const updating = useAppSelector(state => state.appAvailableCourse.updating);
  const updateSuccess = useAppSelector(state => state.appAvailableCourse.updateSuccess);

  const handleClose = () => {
    navigate('/app-available-course-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppCourses({}));
    dispatch(getAppUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appAvailableCourseEntity,
      ...values,
      courses: appCourses.find(it => it.id.toString() === values.courses.toString()),
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
          ...appAvailableCourseEntity,
          courses: appAvailableCourseEntity?.courses?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appAvailableCourse.home.createOrEditLabel" data-cy="AppAvailableCourseCreateUpdateHeading">
            Create or edit a App Available Course
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
                  id="app-available-course-my-suffix-id"
                  label="Id"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField id="app-available-course-my-suffix-courses" name="courses" data-cy="courses" label="Courses" type="select">
                <option value="" key="0" />
                {appCourses
                  ? appCourses.map(otherEntity => (
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
                to="/app-available-course-my-suffix"
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

export default AppAvailableCourseMySuffixUpdate;
