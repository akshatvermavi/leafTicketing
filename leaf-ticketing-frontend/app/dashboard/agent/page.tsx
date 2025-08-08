import ProtectedRoute from '../../../components/ProtectedRoute';

export default function AgentDashboard() {
  return (
    <ProtectedRoute allowedRoles={['AGENT', 'ADMIN']}>
      <div>
        <h1>Support Agent Dashboard</h1>
        {/* Assigned tickets, status change, comment will go here */}
      </div>
    </ProtectedRoute>
  );
} 