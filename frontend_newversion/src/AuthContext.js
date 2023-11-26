import React, { createContext, useContext, useReducer } from 'react';

const AuthContext = createContext();

const initialState = {
  loggedInUserId: null,
};

const authReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      return { ...state, loggedInUserId: action.payload };
    case 'LOGOUT':
      return { ...state, loggedInUserId: null };
    default:
      return state;
  }
};

const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, initialState);

  const login = (userId) => {
    dispatch({ type: 'LOGIN', payload: userId });
  };

  const logout = () => {
    dispatch({ type: 'LOGOUT' });
  };

  return (
    <AuthContext.Provider value={{ loggedInUserId: state.loggedInUserId, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export { AuthProvider, useAuth };