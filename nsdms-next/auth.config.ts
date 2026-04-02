import type { NextAuthConfig } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

export const authConfig = {
  secret: "A3Xq2+9J5H8L4M1N7T0Z6W2V8Y5B3Q1P9R4C7M9K2X5=",
  providers: [
    CredentialsProvider({
      name: "Mock Credentials",
      credentials: {
        email: { label: "Email (admin@ or user@)", type: "email" },
        password: { label: "Password (any)", type: "password" }
      },
      async authorize(credentials) {
        if (!credentials?.email) return null;
        
        // Mock Admin User
        if (credentials.email === "admin@merseta.org.za") {
          return {
            id: "u1_mock_admin",
            name: "Mock Admin",
            email: "admin@merseta.org.za",
            role: "ADMIN"
          };
        }
        
        // Mock Standard User
        if (credentials.email === "user@merseta.org.za") {
          return {
            id: "u2_mock_user",
            name: "Mock Standard User",
            email: "user@merseta.org.za",
            role: "STANDARD"
          };
        }

        return null; // Invalid credentials
      }
    })
  ],
  callbacks: {
    jwt({ token, user }) {
      if (user) {
        // user object is only passed in the very first login cycle
        token.role = user.role;
        token.id = user.id;
      }
      return token;
    },
    session({ session, token }) {
      if (session.user) {
        session.user.role = token.role as string;
        session.user.id = token.id as string;
      }
      return session;
    }
  },
// pages: {
  //   signIn: "/api/auth/signin", // default Next.js auth page is fine for MVP
  // }
} satisfies NextAuthConfig;
