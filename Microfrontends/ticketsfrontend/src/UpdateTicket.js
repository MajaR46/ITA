import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const Update = () => {
    const { id } = useParams(); 
    const [eventName, setEventName] = useState('');
    const [eventDate, setEventDate] = useState('');
    const [eventPrice, setEventPrice] = useState('');
    const [quantity, setQuantity] = useState('');

    useEffect(() => {
        // Fetch ticket data based on id
        fetch(`http://localhost:8100/web/tickets/${id}`)
            .then(response => response.json())
            .then(data => {
                // Set state variables with fetched data
                setEventName(data.eventName);
                setEventDate(data.eventDate);
                setEventPrice(data.eventPrice);
                setQuantity(data.quantity);
            })
            .catch(error => console.error('Error fetching ticket:', error));
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const ticket = { eventName, eventDate, eventPrice, quantity };

        fetch(`http://localhost:8100/web/tickets/${id}`, {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ticket)
        })
        .then(() => {
            console.log('Ticket updated');
            window.location.href = '/'; 
        });
    }

    return (
        <div className="create">
            <h2>Update Ticket</h2>
            <form onSubmit={handleSubmit}>
                <label>Event name:</label>
                <input type="text" required value={eventName} onChange={(e) => setEventName(e.target.value)} />
    
                <label>Event date:</label>
                <input type="text" required value={eventDate} onChange={(e) => setEventDate(e.target.value)} />
    
                <label>Event price:</label>
                <input type="text" required value={eventPrice} onChange={(e) => setEventPrice(e.target.value)} />

                <label>Quantity:</label>
                <input type="text" required value={quantity} onChange={(e) => setQuantity(e.target.value)} />
    
                <button type="submit">Update Ticket</button>
            </form>
        </div>
    );
}

export default Update;
