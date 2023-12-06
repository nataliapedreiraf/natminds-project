// Following.js
import React from 'react';
import './Following.css';

// Componente funcional Following que muestra la cantidad de usuarios seguidos
// y permite manejar clics en la sección de usuarios seguidos
const Following = ({ onClick, count }) => {
  return (
    <div className="sidebar__section" onClick={() => onClick('following')}>
      <p>Siguiendo&nbsp;</p>
      <strong>{count}</strong>
    </div>
  );
};

// Exporta el componente Following para su uso en otros lugares
export default Following;