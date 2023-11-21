import React from 'react';
import { FaUserPlus } from 'react-icons/fa';
import './RightSidebar.css';

const RightSidebar = () => {
  return (
    <div className="right-sidebar">
      {/* Parte 1: Información de Premium */}
      <div className="premium-section">
        <h2 className="sidebar-heading">Premium</h2>
        <p>Obtén acceso exclusivo a contenido premium</p>
        <button className="subscribe-button">Suscríbete</button>
      </div>

      <div className="separator"></div>

      {/* Parte 2: A quien seguir */}
      <div className="who-to-follow-section">
        <h2 className="sidebar-heading">A quien seguir</h2>

        {/* Usuario 1 */}
        <div className="user">
          <img src="https://placekitten.com/50/50" alt="Usuario 1" className="user-image" />
          <div>
            <p className="user-name">Nombre </p>
            <p className="user-handle">@nombreusu</p>
          </div>
          <button className="follow-button">
            <FaUserPlus />
            Follow
          </button>
        </div>

        {/* Usuario 2 */}
        <div className="user">
          <img src="https://placekitten.com/50/50" alt="Usuario 2" className="user-image" />
          <div>
            <p className="user-name">Nombre</p>
            <p className="user-handle">@nombreusu</p>
          </div>
          <button className="follow-button">
            <FaUserPlus />
            Follow
          </button>
        </div>

        {/* Usuario 3 */}
        <div className="user">
          <img src="https://placekitten.com/50/50" alt="Usuario 3" className="user-image" />
          <div>
            <p className="user-name">Nombre</p>
            <p className="user-handle">@nombreusu</p>
          </div>
          <button className="follow-button">
            <FaUserPlus />
            Follow
          </button>
        </div>

        <div className="show-more">
          <p>Enseñame más</p>
        </div>
      </div>

      <div className="separator"></div>

      {/* Parte 3: Tendencias */}
      <div className="trends-section">
        <h2 className="sidebar-heading">Tendencias</h2>
        <div className="hashtag">#Java</div>
        <div className="hashtag">#Spring</div>
        <div className="hashtag">#Hibernate</div>
        <div className="hashtag">#React</div>
        <div className="hashtag">#SQL</div>
        {/* Agrega más tendencias según sea necesario */}
      </div>
    </div>
  );
}

export default RightSidebar;