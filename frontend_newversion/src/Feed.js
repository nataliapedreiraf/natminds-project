import React, { useState, useEffect } from 'react';
import './Feed.css';
import PostForm from './PostForm';
import perfilImage1 from './perfil1.jpg';
import { FaHeart, FaComment } from 'react-icons/fa';

const Feed = () => {
  const [posts, setPosts] = useState([]);

  const handlePostSubmit = (newPost) => {
    setPosts([newPost, ...posts]);
  };

  const fetchPosts = async () => {
    try {
      const response = await fetch('http://localhost:8080/posts/all');
      if (response.ok) {
        const postsData = await response.json();
        setPosts(postsData);
      } else {
        console.error('Error al obtener los posts:', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  const handleLikeClick = async (postId) => {
    try {
        const response = await fetch(`http://localhost:8080/likes/like?postId=${postId}`, {
            method: 'POST',
            credentials: 'include',
        });

        if (response.ok) {
            // No necesitas analizar la respuesta JSON, ya que es un número
            const updatedLikesCount = await response.text();
            console.log('Respuesta del servidor:', updatedLikesCount);

            console.log('Respuesta del servidor:', updatedLikesCount);

            // Utiliza la función de actualización del estado que toma el estado anterior
            setPosts((prevPosts) => {
                console.log('Estado anterior:', prevPosts);

                const updatedPosts = prevPosts.map((prevPost) =>
                    prevPost.postId === postId ? { ...prevPost, likesCount: parseInt(updatedLikesCount) } : prevPost
                );

                console.log('Estado actualizado:', updatedPosts);

                return updatedPosts;
            });
        } else {
            console.error('Error al dar "Me gusta":', response.statusText);
        }
    } catch (error) {
        console.error('Error al realizar la solicitud:', error.message);
    }
};

  const fetchLikesCount = async (postId) => {
    try {
      const response = await fetch(`http://localhost:8080/posts/${postId}/likeCount`);
      if (response.ok) {
        const likesCount = await response.json();
        return likesCount;
      } else {
        console.error('Error al obtener la cantidad de "likes":', response.statusText);
        return 0;
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
      return 0;
    }
  };

  useEffect(() => {
    fetchPosts();
  
    const socket = new WebSocket('ws://localhost:8080/posts/ws');
  
    socket.addEventListener('message', (event) => {
      const newPost = JSON.parse(event.data);
      setPosts((prevPosts) => [newPost, ...prevPosts]);
    });
  
    return () => {
      socket.close();
    };
  }, []);

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
                <p className="post__username">{post.user ? post.user.name : ''}</p>
                <p className="post__handle">{post.user ? post.user.userName : ''}</p>
              </div>
            </div>
            <p className="post__text">{post.text}</p>
            <div className="post-actions">
              <button
                className="post-action-button"
                onClick={() => handleLikeClick(post.postId)}
              >
                <FaHeart /> 
                <span>{post.likesCount || 0}</span>
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