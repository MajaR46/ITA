import React, { useState } from "react";

const Create = () => {
    const [eventName, setEventName] = useState('');
    const [eventDate, setEventDate] = useState('');
    const [eventPrice, setEventPrice] = useState('');
    const [quantity, setQuantity] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const ticket = { eventName, eventDate, eventPrice, quantity };
    
        fetch('http://localhost:8100/web/tickets', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ticket)
        }).then(() => {
            console.log('New ticket added');
            window.location.href = '/'; 
        });
    }
    

    return(
        <div className="create">
            <h2> Add new ticket</h2>
            <form onSubmit={handleSubmit}>
    
                <label> Event name:</label>
                <input type="text" required value={eventName} onChange={(e)=>setEventName(e.target.value)}>
                </input>
    
                <label> Event date:</label>
                <input type="text" required value={eventDate} onChange={(e)=>setEventDate(e.target.value)}>
                </input>
    
                <label> Event price:</label>
                <input type="text" required value={eventPrice} onChange={(e)=>setEventPrice(e.target.value)}>
                </input>

                <label> Quantity</label>
                <input type="text" required value={quantity} onChange={(e)=>setQuantity(e.target.value)}>
                </input>
    
                <button type="submit">Add new ticket</button>
    
            </form>
        </div>
    )
}

export default Create;
