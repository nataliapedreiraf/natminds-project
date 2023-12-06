import React, { useState, useEffect } from 'react';
import './Feed.css';
import PostForm from './PostForm';
import perfilImage1 from './perfil1.png';
import { FaHeart, FaComment } from 'react-icons/fa';

const Feed = () => {
  // Estado para almacenar la lista de posts
  const [posts, setPosts] = useState([]);

  // Funciones para gestionar el almacenamiento local de posts que han recibido "Me gusta"
  const getLocalStorageLikedPosts = () => {
    const likedPosts = localStorage.getItem('likedPosts');
    return likedPosts ? JSON.parse(likedPosts) : {};
  };

  const setLocalStorageLikedPosts = (likedPosts) => {
    localStorage.setItem('likedPosts', JSON.stringify(likedPosts));
  };

  // Efecto secundario para obtener la lista de posts desde el servidor al montar el componente
  useEffect(() => {

    const fetchPosts = async () => {
      try {
        const response = await fetch('http://localhost:8080/posts/all', { method: 'GET', credentials: 'include' });
        if (response.ok) {
          const postsData = await response.json();

          // Añade información sobre la cantidad de "Me gusta" y si el usuario actual dio "Me gusta" a cada post
          const postsWithLikes = await Promise.all(
            postsData.map(async (post) => {
              const likesCount = await fetchLikesCount(post.postId);
              return { ...post, likesCount, userLiked: false };
            })
          );

          // Obtiene los posts que el usuario actual ya ha marcado como "Me gusta"
          const likedPosts = getLocalStorageLikedPosts();

          // Actualiza el estado de los posts con la información de "Me gusta"
          setPosts(
            postsWithLikes.map((post) => ({
              ...post,
              userLiked: likedPosts[post.postId] || false,
            })).reverse()
          );
        } else {
          console.error('Error al obtener los posts:', response.statusText);
        }
      } catch (error) {
        console.error('Error al realizar la solicitud:', error.message);
      }
    };

    // Llama a la función fetchPosts al montar el componente
    fetchPosts();

    // Establece un intervalo para refrescar la lista de posts cada segundo
    const interval = setInterval(() => {
      console.log('This will run every second!');
      fetchPosts();
    }, 1000);

    console.log("Hi")
  }, []); // El efecto se ejecuta solo al montar el componente

  // Función para manejar el clic en el botón de "Me gusta"
  const handleLikeClick = async (postId) => {
    try {
      const response = await fetch(`http://localhost:8080/likes/like?postId=${postId}`, {
        method: 'POST',
        credentials: 'include',
      });

      if (response.ok) {
        const updatedLikesCount = await response.text();

        // Manejar el caso en que la respuesta del servidor es negativa
        const likesCount = Math.max(0, parseInt(updatedLikesCount));

        // Actualiza el estado de los posts, marca el post actual como "Me gusta" en el almacenamiento local
        setPosts((prevPosts) => {
          const updatedPosts = prevPosts.map((prevPost) =>
            prevPost.postId === postId
              ? { ...prevPost, likesCount, userLiked: true }
              : prevPost
          );

          const likedPosts = getLocalStorageLikedPosts();
          likedPosts[postId] = true;
          setLocalStorageLikedPosts(likedPosts);

          return updatedPosts;
        });

        // Agregar la clase jump al botón del corazón
        document.getElementById(`likeButton_${postId}`).classList.add('jump');

        // Quitar la clase jump después de la animación (500 ms)
        setTimeout(() => {
          document.getElementById(`likeButton_${postId}`).classList.remove('jump');
        }, 500);
      } else {
        console.error('Error al dar "Me gusta":', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  // Función para manejar el clic en el botón de "No me gusta"
  const handleUnlikeClick = async (postId) => {
    try {
      const response = await fetch(`http://localhost:8080/likes/unlike?postId=${postId}`, {
        method: 'POST',
        credentials: 'include',
      });

      if (response.ok) {
        // Actualiza el estado de los posts, marca el post actual como "No me gusta" en el almacenamiento local
        setPosts((prevPosts) => {
          const updatedPosts = prevPosts.map((prevPost) =>
            prevPost.postId === postId
              ? {
                ...prevPost,
                likesCount: prevPost.userLiked
                  ? Math.max(0, parseInt(prevPost.likesCount) - 1)
                  : parseInt(prevPost.likesCount),
                userLiked: false,
              }
              : prevPost
          );

          const likedPosts = getLocalStorageLikedPosts();
          if (likedPosts[postId]) {
            likedPosts[postId] = false;
            setLocalStorageLikedPosts(likedPosts);
          }

          return updatedPosts;
        });
      } else {
        console.error('Error al dar "No me gusta":', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  // Función para obtener la cantidad de "Me gusta" de un post
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


  // Función para manejar el envío de un nuevo post
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

      } else {
        console.error('Error al enviar el nuevo post:', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  // Renderiza la interfaz de Feed
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
                <p className="post__username">{post.userName}</p>
                <p className="post__handle">{post.userFullName}</p>
              </div>
            </div>
            <p className="post__text">{post.text}</p>
            <div className="post-actions">
              <button
                id={`likeButton_${post.postId}`}
                className="post-action-button"
                onClick={() => (post.userLiked ? handleUnlikeClick(post.postId) : handleLikeClick(post.postId))}
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

// Exporta el componente Feed para su uso en otros lugares
export default Feed;