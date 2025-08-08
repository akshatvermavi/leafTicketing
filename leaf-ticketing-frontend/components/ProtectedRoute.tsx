"use client";
import React, { ReactNode, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from './AuthProvider';

interface ProtectedRouteProps {
  children: ReactNode;
  allowedRoles: string[];
}

export type { ProtectedRouteProps };

const ProtectedRoute = ({ children, allowedRoles }: ProtectedRouteProps) => {
  const { roles, token } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!token) {
      router.push('/login');
    } else if (allowedRoles.length > 0 && !roles.some(role => allowedRoles.includes(role))) {
      router.push('/unauthorized');
    }
  }, [token, roles, allowedRoles, router]);

  if (!token || (allowedRoles.length > 0 && !roles.some(role => allowedRoles.includes(role)))) {
    return null;
  }

  return <>{children}</>;
};

export default ProtectedRoute; 