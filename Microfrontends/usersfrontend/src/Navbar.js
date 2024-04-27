import { Link } from "react-router-dom";
import React from 'react';


const Navbar = () => {
  return (
    <nav className="navbar">
      <h1>Users API</h1>
      <div className="links">
        <Link to="/">Home</Link>
        <Link to="/createUser" style={{ 
          color: 'white', 
          backgroundColor: '#f1356d',
          borderRadius: '8px' 
        }}>New user</Link>

      </div>
    </nav>
  );
}
 
export default Navbar;