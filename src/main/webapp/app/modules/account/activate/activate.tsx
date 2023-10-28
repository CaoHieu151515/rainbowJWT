import React, { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Row, Col, Alert } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { activateAction, reset } from './activate.reducer';

export const ActivatePage = () => {
  const dispatch = useAppDispatch();

  const [searchParams] = useSearchParams();

  useEffect(() => {
    window.location.href = 'https://vnrainbow.vercel.app'; // Thay thế bằng URL của trang bạn muốn chuyển hướng đến
  }, []);

  const { activationSuccess, activationFailure } = useAppSelector(state => state.activate);

  return (
    <div style={{ visibility: 'hidden', display: 'none' }}>
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
