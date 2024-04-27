import React, { useState } from "react";

const Create = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = { firstName, lastName, email, password };
    
        fetch('http://localhost:8100/web/users', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        }).then(() => {
            console.log('New user added');
            window.location.href = '/'; 
        });
    }
    

    return(
        <div className="create">
            <h2> Add new user</h2>
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
    
                <button type="submit">Add new user</button>
    
            </form>
        </div>
    )
}

export default Create;
