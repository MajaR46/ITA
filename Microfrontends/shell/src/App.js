import React, { lazy, useState, useEffect } from "react";
import { Routes, Route, useNavigate } from "react-router-dom";
import Header from "./header";
import Home from "./home";
import Registration from "./Registration";
import Login from "./Login";
import "./styles.css";

const User = lazy(() => import("UserApp/App"));
const Ticket = lazy(() => import("TicketApp/App"));
const Payment = lazy(() => import("PaymentApp/App"));

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const userLoggedIn = localStorage.getItem("user");
    if (userLoggedIn) {
      setLoggedIn(true);
    } else {
      navigate("/login"); 
    }
  }, [navigate]);

  
  const handleLogout = () => {
    localStorage.removeItem("user");
    setLoggedIn(false); 
    navigate("/login");
  };

  return (
    <div className="App">
      <Header isLoggedIn={loggedIn} onLogout={handleLogout} />
      <React.Suspense fallback={<div>Loading</div>}>
      <Routes>
  <Route path="/" element={<Home />} />
  <Route path="/login" element={<Login />} />
  <Route path="/registration" element={<Registration />} />

  {loggedIn && (
    <>
      <Route path="/user/*" element={<User />} />
      <Route path="/ticket/*" element={<Ticket />} />
      <Route path="/payment/*" element={<Payment />} />
    </>
  )}
</Routes>

      </React.Suspense>
    </div>
  );
}

export default App;
