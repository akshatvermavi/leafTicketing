import React, { useState } from 'react';
import ProtectedRoute from '../../../components/ProtectedRoute';
import TicketList, { Ticket } from '../../../components/TicketList';
import TicketForm from '../../../components/TicketForm';
import CommentThread from '../../../components/CommentThread';

export default function UserDashboard() {
  const [selectedTicket, setSelectedTicket] = useState<Ticket | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [refresh, setRefresh] = useState(false);

  const handleSelect = (ticket: Ticket) => {
    setSelectedTicket(ticket);
    setShowForm(false);
  };

  const handleNew = () => {
    setSelectedTicket(null);
    setShowForm(true);
  };

  const handleSuccess = () => {
    setShowForm(false);
    setSelectedTicket(null);
    setRefresh(r => !r);
  };

  return (
    <ProtectedRoute allowedRoles={['USER', 'ADMIN', 'AGENT']}>
      <div>
        <h1>User Dashboard</h1>
        <button onClick={handleNew}>New Ticket</button>
        <TicketList key={refresh ? 'refresh' : 'no-refresh'} onSelect={handleSelect} />
        {showForm && (
          <TicketForm ticket={selectedTicket} onSuccess={handleSuccess} />
        )}
        {selectedTicket && !showForm && (
          <div>
            <h2>Ticket Details</h2>
            <div>
              <b>Subject:</b> {selectedTicket.subject}<br />
              <b>Description:</b> {selectedTicket.description}<br />
              <b>Status:</b> {selectedTicket.status}<br />
              <b>Priority:</b> {selectedTicket.priority}<br />
              <b>Created At:</b> {selectedTicket.createdAt}<br />
              <b>Updated At:</b> {selectedTicket.updatedAt}<br />
            </div>
            <CommentThread ticketId={selectedTicket.id} />
          </div>
        )}
      </div>
    </ProtectedRoute>
  );
} 