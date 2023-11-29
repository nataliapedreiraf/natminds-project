import React, { useState, useEffect } from 'react';
import { FaUserPlus } from 'react-icons/fa';
import './RightSidebar.css';

const RightSidebar = () => {

  const [usersToFollow, setUsersToFollow] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/users/allisfollowing`, { credentials: 'include' })
      .then(response => response.json())
      .then(data => setUsersToFollow(data))
      .catch(error => console.error('Error fetching users to follow:', error));

  });

  const handleFollowClick = (user) => {
  
    //const method = user.isFollowing ? 'DELETE' : 'GET';

    if (!user.isFollowing) {

      const url = `http://localhost:8080/users/follow/${user.userId}`;
      const method = 'GET';

      fetch(url, { method, credentials: 'include' })
        .then(response => {
          if (response.ok) {
            setUsersToFollow(prevUsers =>
              prevUsers.map(u => (u.userId === user.userId ? { ...u, isFollowing: !u.isFollowing } : u))
            );
          }
        })
        .catch(error => console.error('Error following/unfollowing user:', error));
    } else {

      const url = `http://localhost:8080/users/unfollow/${user.userId}`;
      const method = 'GET';

      fetch(url, { method, credentials: 'include' })
        .then(response => {
          if (response.ok) {
            setUsersToFollow(prevUsers =>
              prevUsers.map(u => (u.userId === user.userId ? { ...u, isFollowing: !u.isFollowing } : u))
            );
          }
        })
        .catch(error => console.error('Error following/unfollowing user:', error));
    }
  };

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

        {usersToFollow.map(user => (
          <div className="user" key={user.userId}>
            <img src={`https://placekitten.com/50/50?user=${user.userId}`} alt={user.userName} className="user-image" />
            <div>
              <p className="user-name">{user.name} {user.lastName}</p>
              <p className="user-handle">{user.userName}</p>
            </div>
            <button className="follow-button" onClick={() => handleFollowClick(user)}>
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