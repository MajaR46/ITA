import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import Create from "./CreateUser";
import Update from "./UpdateUser";
import React from 'react';
import "./styles.css";


function App() {
  return (
    <Router>
      <div className="UserApp">
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/createUser" element={<Create />} />
            <Route path="/update/:userId" element={<Update />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
