import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const Update = () => {
    const { userId } = useParams(); 
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    useEffect(() => {
        // Fetch user data based on userId
        fetch('http://localhost:8100/web/users/' + userId)
            .then(response => response.json())
            .then(data => {
                setFirstName(data.firstName);
                setLastName(data.lastName);
                setEmail(data.email);
                setPassword(data.password);
            })
            .catch(error => console.error('Error fetching user:', error));
    }, [userId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = { firstName, lastName, email, password };

        fetch('http://localhost:8100/web/users/' + userId,  {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        }).then(() => {
            console.log('User updated');
            window.location.href = '/'; 
        });
    }
    

    return(
        <div className="create">
            <h2> Update User</h2>
            <form onSubmit={handleSubmit}>
    
                <label> User name:</label>
                <input type="text" required value={firstName} onChange={(e)=>setFirstName(e.target.value)}>
                </input>
    
                <label> User lastname:</label>
                <input type="text" required value={lastName} onChange={(e)=>setLastName(e.target.value)}>
                </input>
    
                <label> User email:</label>
                <input type="text" required value={email} onChange={(e)=>setEmail(e.target.value)}>
                </input>

                <label> User password:</label>
                <input type="text" required value={password} onChange={(e)=>setPassword(e.target.value)}>
                </input>
    
                <button type="submit">Update User</button>
    
            </form>
        </div>
    )
}

export default Update;
