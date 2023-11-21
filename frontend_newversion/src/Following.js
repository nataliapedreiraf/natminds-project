// Following.js
import React from 'react';
import './Following.css';

const Following = ({ onClick, count }) => {
  return (
    <div className="sidebar__section" onClick={() => onClick('following')}>
      <p>Siguiendo&nbsp;</p>
      <strong>{count}</strong>
    </div>
  );
};

export default Following;