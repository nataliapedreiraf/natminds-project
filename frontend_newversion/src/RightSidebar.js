import React, { useState, useEffect } from 'react';
import { FaUserPlus } from 'react-icons/fa';
import { useAuth } from './AuthContext';  // Asegúrate de proporcionar la ruta correcta
import './RightSidebar.css';

const RightSidebar = () => {
  const { loggedInUserId } = useAuth();  // Obtén el userId del contexto
  const [usersToFollow, setUsersToFollow] = useState([]);

  useEffect(() => {
    console.log("loggedInUserId:", loggedInUserId);

    if (loggedInUserId) {
      fetch(`http://localhost:8080/users/all?userId=${loggedInUserId}`)
        .then(response => response.json())
        .then(data => setUsersToFollow(data))
        .catch(error => console.error('Error fetching users to follow:', error));
    }
  }, [loggedInUserId]);

  const handleFollowClick = (user) => {
    const url = `http://localhost:8080/users/${loggedInUserId}/follow/${user.userId}`;
    const method = user.isFollowing ? 'DELETE' : 'POST';

    fetch(url, { method })
      .then(response => {
        if (response.ok) {
          setUsersToFollow(prevUsers =>
            prevUsers.map(u => (u.userId === user.userId ? { ...u, isFollowing: !u.isFollowing } : u))
          );
        }
      })
      .catch(error => console.error('Error following/unfollowing user:', error));
  };

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
            <img src={`https://placekitten.com/50/50?user=${user.userId}`} alt={user.userName} className="user-image" />
            <div>
              <p className="user-name">{user.name} {user.lastName}</p>
              <p className="user-handle">@{user.userName}</p>
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

export default RightSidebar;