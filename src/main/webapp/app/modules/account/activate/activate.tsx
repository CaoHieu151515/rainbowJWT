import React, { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Row, Col, Alert } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { activateAction, reset } from './activate.reducer';

export const ActivatePage = () => {
  const dispatch = useAppDispatch();

  const [searchParams] = useSearchParams();

  useEffect(() => {
    const key = searchParams.get('key');

    dispatch(activateAction(key));
    return () => {
      dispatch(reset());
      window.location.href = 'https://google.com'; // Thay thế bằng URL của trang back-end của bạn
    };
  }, []);

  const { activationSuccess, activationFailure } = useAppSelector(state => state.activate);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Activation</h1>
          {activationSuccess ? (
            <Alert color="success">
              <strong>Your user account has been activated.</strong>
            </Alert>
          ) : undefined}
          {activationFailure ? (
            <Alert color="danger">
              <strong>Your user could not be activated.</strong>
            </Alert>
          ) : undefined}
        </Col>
      </Row>
    </div>
  );
};

export default ActivatePage;
