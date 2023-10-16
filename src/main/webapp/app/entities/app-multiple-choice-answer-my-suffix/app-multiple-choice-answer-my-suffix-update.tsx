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
import { IAppMultipleChoiceAnswerMySuffix } from 'app/shared/model/app-multiple-choice-answer-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './app-multiple-choice-answer-my-suffix.reducer';

export const AppMultipleChoiceAnswerMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const appQuestions = useAppSelector(state => state.appQuestion.entities);
  const appMultipleChoiceAnswerEntity = useAppSelector(state => state.appMultipleChoiceAnswer.entity);
  const loading = useAppSelector(state => state.appMultipleChoiceAnswer.loading);
  const updating = useAppSelector(state => state.appMultipleChoiceAnswer.updating);
  const updateSuccess = useAppSelector(state => state.appMultipleChoiceAnswer.updateSuccess);

  const handleClose = () => {
    navigate('/app-multiple-choice-answer-my-suffix');
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
      ...appMultipleChoiceAnswerEntity,
      ...values,
      question: appQuestions.find(it => it.id.toString() === values.question.toString()),
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
          ...appMultipleChoiceAnswerEntity,
          question: appMultipleChoiceAnswerEntity?.question?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rainbowApp.appMultipleChoiceAnswer.home.createOrEditLabel" data-cy="AppMultipleChoiceAnswerCreateUpdateHeading">
            Create or edit a App Multiple Choice Answer
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
                  id="app-multiple-choice-answer-my-suffix-id"
                  label="Id"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Answer Text"
                id="app-multiple-choice-answer-my-suffix-answerText"
                name="answerText"
                data-cy="answerText"
                type="text"
              />
              <ValidatedField
                label="Is Correct"
                id="app-multiple-choice-answer-my-suffix-isCorrect"
                name="isCorrect"
                data-cy="isCorrect"
                check
                type="checkbox"
              />
              <ValidatedField
                id="app-multiple-choice-answer-my-suffix-question"
                name="question"
                data-cy="question"
                label="Question"
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
                to="/app-multiple-choice-answer-my-suffix"
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

export default AppMultipleChoiceAnswerMySuffixUpdate;
