import React, { useState, useEffect } from 'react';
import './Feed.css';
import PostForm from './PostForm';
import perfilImage1 from './perfil1.jpg';
import { FaHeart, FaComment } from 'react-icons/fa'; // Importa los íconos de react-icons

const Feed = () => {
  const [posts, setPosts] = useState([]);

  const handlePostSubmit = (newPost) => {
    setPosts([newPost, ...posts]);
  };

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch('http://localhost:8080/posts/all');
        if (response.ok) {
          const postsData = await response.json();
          setPosts(postsData.reverse());
        } else {
          console.error('Error al obtener los posts:', response.statusText);
        }
      } catch (error) {
        console.error('Error al realizar la solicitud:', error.message);
      }
    };

    fetchPosts();

    const socket = new WebSocket('ws://localhost:8080/posts/ws');

    socket.addEventListener('message', (event) => {
      const newPost = JSON.parse(event.data);
      setPosts([newPost, ...posts].reverse());
    });

    return () => {
      socket.close();
    };
  }, [posts]);

  return (
    <div className="feed">
      <div className="feed-header">
        <h1 className="app-name__text">NatMinds</h1>
      </div>

      <PostForm onPostSubmit={handlePostSubmit} />

      <div className="post-list">
        {posts.map((post, index) => (
          <div className="post" key={index}>
            <div className="post-header">
              <img
                src={perfilImage1}
                alt="Profile"
                className="post__profileImage"
              />
              <div className="post__userInfo">
                <p className="post__username">Nombre de Usuario</p>
                <p className="post__handle">@nombreusuario</p>
              </div>
            </div>
            <p className="post__text">{post.text}</p>
            <div className="post-actions">
              <button className="post-action-button">
                <FaHeart />
              </button>
              <button className="post-action-button">
                <FaComment />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Feed;