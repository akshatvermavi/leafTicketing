"use client";
import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { useRouter } from 'next/navigation';

interface AuthContextType {
  user: any;
  roles: string[];
  token: string | null;
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<any>(null);
  const [roles, setRoles] = useState<string[]>([]);
  const [token, setToken] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    // Load from localStorage if available
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    const storedRoles = localStorage.getItem('roles');
    if (storedToken) setToken(storedToken);
    if (storedUser) setUser(JSON.parse(storedUser));
    if (storedRoles) setRoles(JSON.parse(storedRoles));
  }, []);

  const login = async (username: string, password: string) => {
    try {
      const res = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });
      if (!res.ok) return false;
      const data = await res.json();
      setToken(data.token);
      localStorage.setItem('token', data.token);
      // Optionally fetch user info/roles from backend
      // For now, just set dummy user/roles
      setUser({ username });
      setRoles(['USER']); // Replace with actual roles from backend
      localStorage.setItem('user', JSON.stringify({ username }));
      localStorage.setItem('roles', JSON.stringify(['USER']));
      return true;
    } catch {
      return false;
    }
  };

  const logout = () => {
    setUser(null);
    setRoles([]);
    setToken(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('roles');
    router.push('/login');
  };

  return (
    <AuthContext.Provider value={{ user, roles, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within AuthProvider');
  return context;
};
