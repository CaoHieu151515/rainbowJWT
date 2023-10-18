import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AppUserMySuffix from './app-user-my-suffix';
import AppCategoryMySuffix from './app-category-my-suffix';
import AppPaymentMySuffix from './app-payment-my-suffix';
import LinkAccountUserMySuffix from './link-account-user-my-suffix';
import AppProductMySuffix from './app-product-my-suffix';
import AppProductImageMySuffix from './app-product-image-my-suffix';
import AppCourseMySuffix from './app-course-my-suffix';
import AppLessonMySuffix from './app-lesson-my-suffix';
import AppLessonVideoMySuffix from './app-lesson-video-my-suffix';
import AppAvailableCourseMySuffix from './app-available-course-my-suffix';
import AppQuestionMySuffix from './app-question-my-suffix';
import AppMultipleChoiceAnswerMySuffix from './app-multiple-choice-answer-my-suffix';
import AppOrderMySuffix from './app-order-my-suffix';
import AppOrderItemMySuffix from './app-order-item-my-suffix';
import AppPostImageMySuffix from './app-post-image-my-suffix';
import AppCartMySuffix from './app-cart-my-suffix';
import AppPostMySuffix from './app-post-my-suffix';
import AppLessonInfoMySuffix from './app-lesson-info-my-suffix';
import AppQuestionVideoInfoMySuffix from './app-question-video-info-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="app-user-my-suffix/*" element={<AppUserMySuffix />} />
        <Route path="app-category-my-suffix/*" element={<AppCategoryMySuffix />} />
        <Route path="app-payment-my-suffix/*" element={<AppPaymentMySuffix />} />
        <Route path="link-account-user-my-suffix/*" element={<LinkAccountUserMySuffix />} />
        <Route path="app-product-my-suffix/*" element={<AppProductMySuffix />} />
        <Route path="app-product-image-my-suffix/*" element={<AppProductImageMySuffix />} />
        <Route path="app-course-my-suffix/*" element={<AppCourseMySuffix />} />
        <Route path="app-lesson-my-suffix/*" element={<AppLessonMySuffix />} />
        <Route path="app-lesson-video-my-suffix/*" element={<AppLessonVideoMySuffix />} />
        <Route path="app-available-course-my-suffix/*" element={<AppAvailableCourseMySuffix />} />
        <Route path="app-question-my-suffix/*" element={<AppQuestionMySuffix />} />
        <Route path="app-multiple-choice-answer-my-suffix/*" element={<AppMultipleChoiceAnswerMySuffix />} />
        <Route path="app-order-my-suffix/*" element={<AppOrderMySuffix />} />
        <Route path="app-order-item-my-suffix/*" element={<AppOrderItemMySuffix />} />
        <Route path="app-post-image-my-suffix/*" element={<AppPostImageMySuffix />} />
        <Route path="app-cart-my-suffix/*" element={<AppCartMySuffix />} />
        <Route path="app-post-my-suffix/*" element={<AppPostMySuffix />} />
        <Route path="app-lesson-info-my-suffix/*" element={<AppLessonInfoMySuffix />} />
        <Route path="app-question-video-info-my-suffix/*" element={<AppQuestionVideoInfoMySuffix />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
