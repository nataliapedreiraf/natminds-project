import React, { useState } from 'react';
import './PostForm.css';
import perfilImage1 from './perfil1.jpg';
import { useAuth } from './AuthContext';

const PostForm = ({ onPostSubmit }) => {
  const [text, setText] = useState('');
  const { loggedInUserId } = useAuth();

  const handleTextChange = (e) => {
    setText(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Botón clicado');

    try {
      const response = await fetch('http://localhost:8080/posts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          
        },
        body: JSON.stringify({
          text,
          userId: loggedInUserId,
        }),
        credentials: 'include', // Añade esta línea para incluir las credenciales
      });

      if (response.ok) {
        setText('');
        onPostSubmit();
      } else {
        console.error('Error al enviar el nuevo post:', response.statusText);
      }
    } catch (error) {
      console.error('Error al realizar la solicitud:', error.message);
    }
  };

  return (
    <div className="post-form">
      <img
        src={perfilImage1}
        alt="Profile"
        className="post-section__profileImage"
      />
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="¿En qué estás pensando?"
          className="post-input"
          value={text}
          onChange={handleTextChange}
        />
        <button type="submit" className="post-button">
          Post
        </button>
      </form>
    </div>
  );
};

export default PostForm;