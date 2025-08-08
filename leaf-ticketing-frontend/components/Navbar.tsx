"use client";
import React from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from './AuthProvider';

const Navbar: React.FC = () => {
  const { roles, logout } = useAuth();
  const router = useRouter();

  return (
    <nav className="flex gap-4 p-4 bg-gray-100 border-b">
      {roles.includes('USER') && (
        <button onClick={() => router.push('/dashboard/user')}>User Dashboard</button>
      )}
      {roles.includes('AGENT') && (
        <button onClick={() => router.push('/dashboard/agent')}>Agent Dashboard</button>
      )}
      {roles.includes('ADMIN') && (
        <button onClick={() => router.push('/dashboard/admin')}>Admin Panel</button>
      )}
      <button onClick={logout} className="ml-auto">Logout</button>
    </nav>
  );
};

export default Navbar;