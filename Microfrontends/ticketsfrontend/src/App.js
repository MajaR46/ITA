import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import Create from "./CreateTicket";
import Update from "./UpdateTicket";
import React from 'react';
import "./styles.css";


function App() {
  return (
    <Router>
      <div className="TicketApp">
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/create" element={<Create />} />
            <Route path="/update/:id" element={<Update />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
