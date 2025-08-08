import React, { useState } from 'react';
import { useAuth } from './AuthProvider';
import { Ticket } from './TicketList';

interface TicketFormProps {
  ticket?: Ticket | null;
  onSuccess: () => void;
}

const priorities = ['LOW', 'MEDIUM', 'HIGH', 'URGENT'];

const TicketForm: React.FC<TicketFormProps> = ({ ticket, onSuccess }) => {
  const { token } = useAuth();
  const [subject, setSubject] = useState(ticket?.subject || '');
  const [description, setDescription] = useState(ticket?.description || '');
  const [priority, setPriority] = useState(ticket?.priority || 'LOW');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    const method = ticket ? 'PUT' : 'POST';
    const url = ticket ? `/api/tickets/${ticket.id}` : '/api/tickets';
    const res = await fetch(url, {
      method,
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ subject, description, priority }),
    });
    setLoading(false);
    if (res.ok) {
      onSuccess();
    } else {
      alert('Failed to save ticket');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-2">
      <h2>{ticket ? 'Edit Ticket' : 'New Ticket'}</h2>
      <div>
        <label>Subject:</label>
        <input value={subject} onChange={e => setSubject(e.target.value)} required />
      </div>
      <div>
        <label>Description:</label>
        <textarea value={description} onChange={e => setDescription(e.target.value)} required />
      </div>
      <div>
        <label>Priority:</label>
        <select value={priority} onChange={e => setPriority(e.target.value)}>
          {priorities.map(p => (
            <option key={p} value={p}>{p}</option>
          ))}
        </select>
      </div>
      <button type="submit" disabled={loading}>{loading ? 'Saving...' : 'Save Ticket'}</button>
    </form>
  );
};

export default TicketForm; 