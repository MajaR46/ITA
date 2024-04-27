import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; 

const Home = () => {
    const [error, setError] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [tickets, setTickets] = useState([]);

    const fetchTickets = async () => {
        try {
            const response = await fetch('http://localhost:8100/web/tickets');
            if (!response.ok) {
                throw new Error('Failed to fetch tickets');
            }
            const responseData = await response.json();
            if (responseData.error) {
                throw new Error(responseData.message || 'Unknown error');
            }
            setTickets(responseData.data); 
            setIsPending(false);
        } catch (error) {
            setError(error.message);
            setIsPending(false);
        }
    };
    



    useEffect(() => {
        fetchTickets();
    }, []);

    const handleDelete = async (ticketId) => {
        try {
            const response = await fetch(`http://localhost:8100/web/tickets/${ticketId}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete ticket');
            }
            setTickets(tickets.filter(ticket => ticket.id !== ticketId));
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className="home">
            {error && <div>{error}</div>}
            {isPending && <div>Loading...</div>}
            <TicketList tickets={tickets} handleDelete={handleDelete} />
        </div>
    );
};

const TicketList = ({ tickets, handleDelete }) => {
    return (
        <div>
         <h2> List of all tickets</h2>
            {tickets.map(ticket => (
                <div className="user-preview" key={ticket.id}>
                    
                    <p>Event Name: {ticket.eventName}</p>
                    <p>Event Date: {ticket.eventDate}</p>
                    <p>Event Price: {ticket.eventPrice}</p>
                    <p>Quantity: {ticket.quantity}</p>
                    <button onClick={() => handleDelete(ticket.id)}>Delete</button>
                    <Link to={`/update/${ticket.id}`}>
                        <button>Update</button>
                    </Link>                </div>
            ))}
        </div>
    );
};



export default Home;
