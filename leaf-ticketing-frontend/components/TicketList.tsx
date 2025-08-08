import React, { useEffect, useState } from 'react';
import { useAuth } from './AuthProvider';

export interface Ticket {
  id: number;
  subject: string;
  description: string;
  priority: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

interface TicketListProps {
  onSelect: (ticket: Ticket) => void;
}

const TicketList: React.FC<TicketListProps> = ({ onSelect }) => {
  const { token } = useAuth();
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTickets = async () => {
      setLoading(true);
      const res = await fetch('/api/tickets', {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (res.ok) {
        const data = await res.json();
        setTickets(data);
      }
      setLoading(false);
    };
    fetchTickets();
  }, [token]);

  if (loading) return <div>Loading tickets...</div>;

  return (
    <div>
      <h2>Your Tickets</h2>
      <ul>
        {tickets.map(ticket => (
          <li key={ticket.id}>
            <button onClick={() => onSelect(ticket)}>
              {ticket.subject} ({ticket.status})
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TicketList; 