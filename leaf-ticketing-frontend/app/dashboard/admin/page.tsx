import ProtectedRoute from '../../../components/ProtectedRoute';

export default function AdminDashboard() {
  return (
    <ProtectedRoute allowedRoles={['ADMIN']}>
      <div>
        <h1>Admin Panel</h1>
        {/* User management, ticket override will go here */}
      </div>
    </ProtectedRoute>
  );
} 