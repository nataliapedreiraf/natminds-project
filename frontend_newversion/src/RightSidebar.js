// RightSidebar.js
import React, { useState, useEffect } from 'react';
import { FaUserPlus } from 'react-icons/fa';
import './RightSidebar.css';
import perfilImage1 from './perfil1.png';

// Componente funcional RightSidebar que representa la barra lateral derecha con información de Premium,
// sugerencias de usuarios a seguir y tendencias
const RightSidebar = () => {
  // Estado para almacenar la lista de usuarios sugeridos para seguir
  const [usersToFollow, setUsersToFollow] = useState([]);

  // Efecto de montaje para obtener la lista de usuarios sugeridos al cargar el componente
  useEffect(() => {
    // Realizar la solicitud al backend para obtener la lista de usuarios sugeridos
    fetch(`http://localhost:8080/users/allisfollowing`, { credentials: 'include' })
      .then(response => response.json())
      .then(data => setUsersToFollow(data))
      .catch(error => console.error('Error fetching users to follow:', error));

  }, []);

  // Función para manejar el clic en el botón de seguir o dejar de seguir a un usuario
  const handleFollowClick = (user) => {
    if (!user.isFollowing) {
      // Realizar la solicitud al backend para seguir al usuario
      const url = `http://localhost:8080/users/follow/${user.userId}`;
      const method = 'GET';

      fetch(url, { method, credentials: 'include' })
        .then(response => {
          if (response.ok) {
            // Actualizar el estado para reflejar el cambio en el estado de seguimiento del usuario
            setUsersToFollow(prevUsers =>
              prevUsers.map(u => (u.userId === user.userId ? { ...u, isFollowing: !u.isFollowing } : u))
            );
          }
        })
        .catch(error => console.error('Error following/unfollowing user:', error));

      // Agrega la clase jump para el efecto de salto
      document.getElementById(`followButton_${user.userId}`).classList.add('jump');

      // Elimina la clase jump después de 500ms
      setTimeout(() => {
        document.getElementById(`followButton_${user.userId}`).classList.remove('jump');
      }, 500);
    } else {
      // Realizar la solicitud al backend para dejar de seguir al usuario
      const url = `http://localhost:8080/users/unfollow/${user.userId}`;
      const method = 'GET';

      fetch(url, { method, credentials: 'include' })
        .then(response => {
          if (response.ok) {
            // Actualizar el estado para reflejar el cambio en el estado de seguimiento del usuario
            setUsersToFollow(prevUsers =>
              prevUsers.map(u => (u.userId === user.userId ? { ...u, isFollowing: !u.isFollowing } : u))
            );
          }
        })
        .catch(error => console.error('Error following/unfollowing user:', error));
    }
  };

  // Renderiza la barra lateral derecha con información de Premium, usuarios sugeridos y tendencias
  return (
    <div className="right-sidebar">

      <div className="premium-section">
        <h2 className="sidebar-heading">Premium</h2>
        <p>Obtén acceso exclusivo a contenido premium</p>
        <button className="subscribe-button">Suscríbete</button>
      </div>

      <div className="separator"></div>


      <div className="who-to-follow-section">
        <h2 className="sidebar-heading">A quien seguir</h2>

        {usersToFollow.map(user => (
          <div className="user" key={user.userId}>
            <img src={perfilImage1} alt={user.userName} className="user-image" />
            <div>
              <p className="user-name">{user.name} {user.lastName}</p>
              <p className="user-handle">{user.userName}</p>
            </div>
            <button
              id={`followButton_${user.userId}`}
              className="follow-button"
              onClick={() => handleFollowClick(user)}
            >
              <FaUserPlus />
              {user.isFollowing ? 'Unfollow' : 'Follow'}
            </button>
          </div>
        ))}

        <div className="show-more">
          <p>Enseñame más</p>
        </div>
      </div>

      <div className="separator"></div>


      <div className="trends-section">
        <h2 className="sidebar-heading">Tendencias</h2>
        <div className="hashtag">#Java</div>
        <div className="hashtag">#Spring</div>
        <div className="hashtag">#Hibernate</div>
        <div className="hashtag">#React</div>
        <div className="hashtag">#SQL</div>

      </div>
    </div>
  );
}

// Exporta el componente RightSidebar para su uso en otros lugares
export default RightSidebar;