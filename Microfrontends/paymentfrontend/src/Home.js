import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; 

const Home = () => {
    const [error, setError] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [payments, setPayments] = useState([]);

    const fetchPayments = async () => {
        try {
            const response = await fetch('http://localhost:8100/web/payments');
            if (!response.ok) {
                throw new Error('Failed to fetch payments');
            }
            const data = await response.json();
            setPayments(data);
            setIsPending(false);
        } catch (error) {
            setError(error.message);
            setIsPending(false);
        }
    };

    useEffect(() => {
        fetchPayments();
    }, []);

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`http://localhost:8100/web/payments/${id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete user');
            }
            setPayments(payments.filter(payment => payment.id !== id));
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className="home">
            {error && <div>{error}</div>}
            {isPending && <div>Loading...</div>}
            <PaymentList payments={payments} handleDelete={handleDelete} />
        </div>
    );
};

const PaymentList = ({ payments, handleDelete }) => {
    return (
        <div>
         <h2> List of all payments</h2>
            {payments.map(payment => (
                <div className="user-preview" key={payment.id}>
                    
                    <p>UserId: {payment.userId}</p>
                    <p>TicketId: {payment.ticketId}</p>
                    <p>Amount: {payment.amount}</p>
                    <p>Payment date: {payment.paymentDate}</p>
                    <p>Payment type: {payment.paymentType}</p>
                    <button onClick={() => handleDelete(payment.id)}>Delete</button>
                    <Link to={`/update/${payment.id}`}>
                        <button>Update</button>
                    </Link>                </div>
            ))}
        </div>
    );
};



export default Home;
