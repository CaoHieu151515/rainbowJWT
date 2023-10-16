import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/app-user-my-suffix">
        App User
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-category-my-suffix">
        App Category
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-payment-my-suffix">
        App Payment
      </MenuItem>
      <MenuItem icon="asterisk" to="/link-account-user-my-suffix">
        Link Account User
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-product-my-suffix">
        App Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-product-image-my-suffix">
        App Product Image
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-course-my-suffix">
        App Course
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-lesson-my-suffix">
        App Lesson
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-lesson-video-my-suffix">
        App Lesson Video
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-available-course-my-suffix">
        App Available Course
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-question-my-suffix">
        App Question
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-multiple-choice-answer-my-suffix">
        App Multiple Choice Answer
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-order-my-suffix">
        App Order
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-order-item-my-suffix">
        App Order Item
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-post-image-my-suffix">
        App Post Image
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-cart-my-suffix">
        App Cart
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-post-my-suffix">
        App Post
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-lesson-info-my-suffix">
        App Lesson Info
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
