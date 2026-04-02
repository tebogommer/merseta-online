import NextAuth from "next-auth";
import { authConfig } from "./auth.config";

// Setup edge-compatible middleware
export default NextAuth(authConfig).auth((req) => {
  const isLoggedIn = !!req.auth;
  const path = req.nextUrl.pathname;
  const isProtected = path.startsWith('/organisations') || 
                      path.startsWith('/dashboard') ||
                      path.startsWith('/admin') ||
                      path.startsWith('/etqa') ||
                      path.startsWith('/workplace') ||
                      path.startsWith('/learners');

  if (isProtected && !isLoggedIn) {
     return Response.redirect(new URL("/api/auth/signin", req.nextUrl));
  }
});

// Matcher to skip static files and API routes where possible
export const config = {
  matcher: ['/((?!api|_next/static|_next/image|favicon.ico).*)'],
};
