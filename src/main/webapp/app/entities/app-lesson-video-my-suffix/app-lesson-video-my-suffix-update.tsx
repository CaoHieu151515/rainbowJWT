import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppLessonInfoMySuffix } from 'app/shared/model/app-lesson-info-my-suffix.model';
import { getEntities as getAppLessonInfos } from 'app/entities/app-lesson-info-my-suffix/app-lesson-info-my-suffix.reducer';
import { IAppLessonVideoMySuffix } from 'app/shared/model/app-lesson-video-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-lesson-video-my-suffix.reducer';

export const AppLessonVideoMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appLessonInfos = useAppSelector(state => state.appLessonInfo.entities);
  const appLessonVideoEntity = useAppSelector(state => state.appLessonVideo.entity);
  const loading = useAppSelector(state => state.appLessonVideo.loading);
  const updating = useAppSelector(state => state.appLessonVideo.updating);
  const updateSuccess = useAppSelector(state => state.appLessonVideo.updateSuccess);

  const handleClose = () => {
    navigate('/app-lesson-video-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAppLessonInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appLessonVideoEntity,
      ...values,
      lessonInfo: appLessonInfos.find(it => it.id.toString() === values.lessonInfo.toString()),
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
          ...appLessonVideoEntity,
          lessonInfo: appLessonVideoEntity?.lessonInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appLessonVideo.home.createOrEditLabel" data-cy="AppLessonVideoCreateUpdateHeading">
            Create or edit a App Lesson Video
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
                <ValidatedField name="id" required readOnly id="app-lesson-video-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Video Url" id="app-lesson-video-my-suffix-videoUrl" name="videoUrl" data-cy="videoUrl" type="text" />
              <ValidatedField
                id="app-lesson-video-my-suffix-lessonInfo"
                name="lessonInfo"
                data-cy="lessonInfo"
                label="Lesson Info"
                type="select"
              >
                <option value="" key="0" />
                {appLessonInfos
                  ? appLessonInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-lesson-video-my-suffix" replace color="info">
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

export default AppLessonVideoMySuffixUpdate;
