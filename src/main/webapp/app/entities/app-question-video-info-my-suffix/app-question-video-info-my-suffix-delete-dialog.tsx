import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './app-question-video-info-my-suffix.reducer';

export const AppQuestionVideoInfoMySuffixDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const appQuestionVideoInfoEntity = useAppSelector(state => state.appQuestionVideoInfo.entity);
  const updateSuccess = useAppSelector(state => state.appQuestionVideoInfo.updateSuccess);

  const handleClose = () => {
    navigate('/app-question-video-info-my-suffix');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(appQuestionVideoInfoEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="appQuestionVideoInfoDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="rainbowApp.appQuestionVideoInfo.delete.question">
        Are you sure you want to delete App Question Video Info {appQuestionVideoInfoEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-appQuestionVideoInfo" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AppQuestionVideoInfoMySuffixDeleteDialog;
