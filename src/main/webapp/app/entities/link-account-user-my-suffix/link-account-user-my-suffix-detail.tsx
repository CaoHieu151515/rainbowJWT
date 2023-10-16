import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './link-account-user-my-suffix.reducer';

export const LinkAccountUserMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const linkAccountUserEntity = useAppSelector(state => state.linkAccountUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="linkAccountUserDetailsHeading">Link Account User</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{linkAccountUserEntity.id}</dd>
          <dt>User</dt>
          <dd>{linkAccountUserEntity.user ? linkAccountUserEntity.user.id : ''}</dd>
          <dt>App User</dt>
          <dd>{linkAccountUserEntity.appUser ? linkAccountUserEntity.appUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/link-account-user-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/link-account-user-my-suffix/${linkAccountUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LinkAccountUserMySuffixDetail;
