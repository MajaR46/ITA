import { Link } from "react-router-dom";
import React from 'react';


const Navbar = () => {
  return (
    <nav className="navbar">
      <h1>Payments API</h1>
      <div className="links">
        <Link to="/">Home</Link>
        <Link to="/createPayment" style={{ 
          color: 'white', 
          backgroundColor: '#6CB4EE',
          borderRadius: '8px' 
        }}>New ticket</Link>

      </div>
    </nav>
  );
}
 
export default Navbar;