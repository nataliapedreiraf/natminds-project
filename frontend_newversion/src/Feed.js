import React, { useState, useEffect } from 'react';
import './Feed.css';
import PostForm from './PostForm';
import perfilImage1 from './perfil1.jpg';
import { FaHeart, FaComment } from 'react-icons/fa';

const Feed = () => {
  const [posts, setPosts] = useState([]);

  const fetchPosts = async () => {
    try {
      const response = await fetch('http://localhost:8080/posts/all');
      if (response.ok) {
        const postsData = await response.json();
        const postsWithLikes = await Promise.all(
          postsData.map(async (post) => {
            const likesCount = await fetchLikesCount(post.postId);
            return { ...post, likesCount };
          })
        );
        setPosts(postsWithLikes.reverse()); // Invertir el orden para mostrar el más reciente arriba
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
        const updatedLikesCount = await response.text();

        setPosts((prevPosts) => {
          const updatedPosts = prevPosts.map((prevPost) =>
            prevPost.postId === postId ? { ...prevPost, likesCount: parseInt(updatedLikesCount) } : prevPost
          );

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

    const socket = new WebSocket('ws://localhost:8080/ws');

    socket.addEventListener('message', (event) => {
      const newPost = JSON.parse(event.data);
      setPosts((prevPosts) => [newPost, ...prevPosts]);
    });

    return () => {
      socket.close();
    };
  }, []);

  const handlePostSubmit = async (newPost) => {
    try {
      const response = await fetch('http://localhost:8080/posts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newPost),
        credentials: 'include',
      });

      if (response.ok) {
        // No necesitas volver a llamar a fetchPosts aquí, ya que el nuevo post se añadirá en tiempo real
      } else {
        console.error('Error al enviar el nuevo post:', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

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
              <img src={perfilImage1} alt="Profile" className="post__profileImage" />
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