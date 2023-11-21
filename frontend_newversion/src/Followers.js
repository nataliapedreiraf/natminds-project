// Followers.js
import React from 'react';
import './Followers.css';

const Followers = ({ onClick, count }) => {
  return (
    <div className="sidebar__section" onClick={() => onClick('followers')}>
      <p>Seguidores&nbsp;</p>
      <strong>{count}</strong>
    </div>
  );
};

export default Followers;