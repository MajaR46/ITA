import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const savedEmail = localStorage.getItem("email");
    const savedPassword = localStorage.getItem("password");

    if (savedEmail) setEmail(savedEmail);
    if (savedPassword) setPassword(savedPassword);
  }, []);

const handleSubmit = async (e) => {
  e.preventDefault();

  try {
    const response = await fetch(
      `http://localhost:8100/web/users/email/${email}`,
      {
        method: "GET",
      }
    );

    if (response.ok) {
      const userData = await response.json();
      console.log("User data:", userData);

      if (userData && userData.password === password) {
        localStorage.setItem("user", JSON.stringify(userData));
        navigate("/");
      } else {
        alert("Login failed. Invalid email or password.");
      }
    } else {
      alert("Login failed. User not found.");
    }
  } catch (error) {
    console.error("Error:", error.message);
  }
};

  

  return (
    <div className="login">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Email:
          <input
          class="input"
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </label>
        <br />
        <label>
          Password:
          <input
                    class="input"

            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </label>
        <br />
        <button class="login" type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;