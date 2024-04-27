import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom

const Home = () => {
    const [error, setError] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [users, setUsers] = useState([]);

    const fetchUsers = async () => {
        try {
            const response = await fetch('http://localhost:8100/web/users');
            if (!response.ok) {
                throw new Error('Failed to fetch users');
            }
            const data = await response.json();
            setUsers(data);
            setIsPending(false);
        } catch (error) {
            setError(error.message);
            setIsPending(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleDelete = async (userId) => {
        try {
            const response = await fetch(`http://localhost:8100/web/users/${userId}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete user');
            }
            setUsers(users.filter(user => user.userId !== userId));
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className="home">
            {error && <div>{error}</div>}
            {isPending && <div>Loading...</div>}
            <UserList users={users} handleDelete={handleDelete} />
        </div>
    );
};

const UserList = ({ users, handleDelete }) => {
    return (
        <div>
         <h2> List of all users</h2>
            {users.map(user => (
                <div className="user-preview" key={user.userId}>
                    
                    <p>First name: {user.firstName}</p>
                    <p>Last name: {user.lastName}</p>
                    <p>Email: {user.email}</p>
                    <button onClick={() => handleDelete(user.userId)}>Delete</button>
                    <Link to={`/update/${user.userId}`}>
                        <button>Update</button>
                    </Link>                </div>
            ))}
        </div>
    );
};



export default Home;
