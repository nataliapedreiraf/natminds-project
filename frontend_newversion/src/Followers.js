// Followers.js
import React from 'react';
import './Followers.css';

// Componente funcional Followers que muestra la cantidad de seguidores
// y permite manejar clics en la sección de seguidores
const Followers = ({ onClick, count }) => {
  return (
    <div className="sidebar__section" onClick={() => onClick('followers')}>
      <p>Seguidores&nbsp;</p>
      <strong>{count}</strong>
    </div>
  );
};

// Exporta el componente Followers para su uso en otros lugares
export default Followers;