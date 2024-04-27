import React, { useState } from "react";

const Create = () => {
    const [userId, setUserId] = useState('');
    const [ticketId, setTicketId] = useState('');
    const [amount, setAmount] = useState('');
    const [pamynetDate, setPaymentDate] = useState('');
    const [paymentType, setPaymentType] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const payment = { userId, ticketId, amount, pamynetDate, paymentType };
    
        fetch('http://localhost:8100/web/payments', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payment)
        }).then(() => {
            console.log('New payment added');
            window.location.href = '/'; 
        });
    }
    

    return(
        <div className="create">
            <h2> Add new payment</h2>
            <form onSubmit={handleSubmit}>
    
                <label> User ID:</label>
                <input type="text" required value={userId} onChange={(e)=>setUserId(e.target.value)}>
                </input>
    
                <label> Ticket Id:</label>
                <input type="text" required value={ticketId} onChange={(e)=>setTicketId(e.target.value)}>
                </input>
    
                <label> Amount :</label>
                <input type="text" required value={amount} onChange={(e)=>setAmount(e.target.value)}>
                </input>

                <label> Payment date:</label>
                <input type="text" required value={pamynetDate} onChange={(e)=>setPaymentDate(e.target.value)}>
                </input>

                <label> Payment type:</label>
                <input type="text" required value={paymentType} onChange={(e)=>setPaymentType(e.target.value)}>
                </input>
    
                <button type="submit">Add new payment</button>
    
            </form>
        </div>
    )
}

export default Create;
