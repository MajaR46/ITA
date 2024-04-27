import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const Update = () => {
    const { id } = useParams(); 
    const [userId, setUserId] = useState('');
    const [ticketId, setTicketId] = useState('');
    const [amount, setAmount] = useState('');
    const [paymentDate, setPaymentDate] = useState('');
    const [paymentType, setPaymentType] = useState('');

    useEffect(() => {
        // Fetch user data based on userId
        fetch('http://localhost:8100/web/payments/' + id)
            .then(response => response.json())
            .then(data => {
                setUserId(data.userId);
                setTicketId(data.ticketId);
                setAmount(data.amount);
                setPaymentDate(data.paymentDate);
                setPaymentType(data.paymentType);
            })
            .catch(error => console.error('Error fetching payment:', error));
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const payment = { userId, ticketId, amount, paymentDate, paymentType };

        fetch('http://localhost:8100/web/payments/' + id,  {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payment)
        }).then(() => {
            console.log('Payment updated');
            window.location.href = '/'; 
        
        });
    }
    

    return(
        <div className="create">
            <h2> Update Payment</h2>
            <form onSubmit={handleSubmit}>
    
                <label> User id:</label>
                <input type="text" required value={userId} onChange={(e)=>setUserId(e.target.value)} />
    
                <label> Ticket id:</label>
                <input type="text" required value={ticketId} onChange={(e)=>setTicketId(e.target.value)} />
    
                <label> Amount:</label>
                <input type="text" required value={amount} onChange={(e)=>setAmount(e.target.value)} />

                <label> Payment date:</label>
                <input type="date" required value={paymentDate} onChange={(e)=>setPaymentDate(e.target.value)} />
    
                <label> Payment type:</label>
                <input type="text" required value={paymentType} onChange={(e)=>setPaymentType(e.target.value)} />

                <button type="submit">Update Payment</button>
    
            </form>
        </div>
    )
}

export default Update;
