import React from 'react';
import { FaTwitter, FaHashtag, FaBell, FaEnvelope, FaBookmark, FaListUl, FaUser, FaSearch, FaEllipsisH } from 'react-icons/fa';
import './Sidebar.css';
import './Followers.css';
import Followers from './Followers';
import Following from './Following';
import perfilImage1 from './perfil1.png';

// Componente funcional Sidebar que representa la barra lateral izquierda con opciones de navegación y perfil del usuario
const Sidebar = ({ userData, onLogout }) => {
  // Función para manejar el clic en un elemento de la barra lateral
  const handleItemClick = (item) => {
    console.log(`Clic en ${item}`);
  };

  // Función para manejar el clic en el botón de cerrar sesión
  const handleLogout = () => {
    // Lógica para cerrar sesión
    onLogout();
  };

  // Renderiza la barra lateral izquierda con opciones de navegación y perfil del usuario
  return (
    <div className="sidebar">
      <div className="sidebar__profile">
        <img
          src={perfilImage1}
          alt="Profile"
          className="sidebar__profileImage"
        />
        <div className="sidebar__profileInfo">
          <p className="sidebar__profileName">{userData.name}</p>
          <p className="sidebar__profileHandle">@{userData.userName}</p>
        </div>
      </div>
      <div className="sidebar__counts">
        <Following onClick={handleItemClick} count={100} />
        <Followers onClick={handleItemClick} count={200} />
      </div>
      <div className="sidebar__section">
        <FaUser className="sidebar__icon" />
        <p>Perfil</p>
      </div>
      <div className="sidebar__section">
        <FaSearch className="sidebar__icon" />
        <p>Buscar</p>
      </div>
      <div className="sidebar__section">
        <FaBookmark className="sidebar__icon" />
        <p>Post que te gustan</p>
      </div>
      <div className="sidebar__section">
        <FaHashtag className="sidebar__icon" />
        <p>Explorar</p>
      </div>
      <div className="sidebar__section">
        <FaBell className="sidebar__icon" />
        <p>Notificaciones</p>
      </div>
      <div className="sidebar__section">
        <FaEnvelope className="sidebar__icon" />
        <p>Mensajes</p>
      </div>
      <div className="sidebar__section" onClick={handleLogout}>
        <FaEllipsisH className="sidebar__icon" />
        <p>Cerrar sesión</p>
      </div>
    </div>
  );
};

// Exporta el componente Sidebar para su uso en otros lugares
export default Sidebar;