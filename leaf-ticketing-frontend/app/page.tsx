"use client";
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '../components/AuthProvider';

export default function Home() {
  const { token, roles } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!token) {
      router.push('/login');
    } else if (roles.includes('ADMIN')) {
      router.push('/dashboard/admin');
    } else if (roles.includes('AGENT')) {
      router.push('/dashboard/agent');
    } else {
      router.push('/dashboard/user');
    }
  }, [token, roles, router]);

  return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="text-center">
        <h1 className="text-2xl font-bold mb-4">Leaf Ticketing System</h1>
        <p>Redirecting...</p>
      </div>
    </div>
  );
}
