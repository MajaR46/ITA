import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import Create from "./CreatePayment";
import Update from "./UpdatePayment";
import React from 'react';
import "./styles.css";



function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/createPayment" element={<Create />} />
            <Route path="/update/:id" element={<Update />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
