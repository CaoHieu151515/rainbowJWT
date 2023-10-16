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
import { IAppAvailableCourseMySuffix } from 'app/shared/model/app-available-course-my-suffix.model';
import { getEntities as getAppAvailableCourses } from 'app/entities/app-available-course-my-suffix/app-available-course-my-suffix.reducer';
import { IAppCartMySuffix } from 'app/shared/model/app-cart-my-suffix.model';
import { getEntities as getAppCarts } from 'app/entities/app-cart-my-suffix/app-cart-my-suffix.reducer';
import { IAppUserMySuffix } from 'app/shared/model/app-user-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-user-my-suffix.reducer';

export const AppUserMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appCourses = useAppSelector(state => state.appCourse.entities);
  const appAvailableCourses = useAppSelector(state => state.appAvailableCourse.entities);
  const appCarts = useAppSelector(state => state.appCart.entities);
  const appUserEntity = useAppSelector(state => state.appUser.entity);
  const loading = useAppSelector(state => state.appUser.loading);
  const updating = useAppSelector(state => state.appUser.updating);
  const updateSuccess = useAppSelector(state => state.appUser.updateSuccess);

  const handleClose = () => {
    navigate('/app-user-my-suffix');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAppCourses({}));
    dispatch(getAppAvailableCourses({}));
    dispatch(getAppCarts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dob = convertDateTimeToServer(values.dob);

    const entity = {
      ...appUserEntity,
      ...values,
      courses: mapIdList(values.courses),
      availableCourses: mapIdList(values.availableCourses),
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
          dob: displayDefaultDateTime(),
        }
      : {
          ...appUserEntity,
          dob: convertDateTimeFromServer(appUserEntity.dob),
          courses: appUserEntity?.courses?.map(e => e.id.toString()),
          availableCourses: appUserEntity?.availableCourses?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appUser.home.createOrEditLabel" data-cy="AppUserCreateUpdateHeading">
            Create or edit a App User
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
                <ValidatedField name="id" required readOnly id="app-user-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Name" id="app-user-my-suffix-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Gender" id="app-user-my-suffix-gender" name="gender" data-cy="gender" type="text" />
              <ValidatedField
                label="Dob"
                id="app-user-my-suffix-dob"
                name="dob"
                data-cy="dob"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Status" id="app-user-my-suffix-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Courses" id="app-user-my-suffix-courses" data-cy="courses" type="select" multiple name="courses">
                <option value="" key="0" />
                {appCourses
                  ? appCourses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Available Courses"
                id="app-user-my-suffix-availableCourses"
                data-cy="availableCourses"
                type="select"
                multiple
                name="availableCourses"
              >
                <option value="" key="0" />
                {appAvailableCourses
                  ? appAvailableCourses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user-my-suffix" replace color="info">
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

export default AppUserMySuffixUpdate;
