import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './app-multiple-choice-answer-my-suffix.reducer';

export const AppMultipleChoiceAnswerMySuffixDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const appMultipleChoiceAnswerEntity = useAppSelector(state => state.appMultipleChoiceAnswer.entity);
  const updateSuccess = useAppSelector(state => state.appMultipleChoiceAnswer.updateSuccess);

  const handleClose = () => {
    navigate('/app-multiple-choice-answer-my-suffix');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(appMultipleChoiceAnswerEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="appMultipleChoiceAnswerDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="rainbowApp.appMultipleChoiceAnswer.delete.question">
        Are you sure you want to delete App Multiple Choice Answer {appMultipleChoiceAnswerEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-appMultipleChoiceAnswer" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AppMultipleChoiceAnswerMySuffixDeleteDialog;
