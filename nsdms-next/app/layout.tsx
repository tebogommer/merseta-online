import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { auth, signIn, signOut } from "@/auth";
import { Providers } from "@/components/providers";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "NSDMS Next - Organisation MVP",
  description: "Modernized NSDMS Platform",
};

import { Sidebar } from "@/components/layout/sidebar";
import { Topbar } from "@/components/layout/topbar";
import { ThemeProvider } from "@/components/theme-provider";
import { Toaster } from "sonner";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className={`${inter.className} bg-gray-50 dark:bg-slate-950 text-slate-900 dark:text-slate-100 h-screen overflow-hidden`}>
        <ThemeProvider
            attribute="class"
            defaultTheme="system"
            enableSystem
            disableTransitionOnChange
        >
          <Providers>
            <div className="flex h-full w-full">
              <Sidebar />
              <div className="flex flex-col flex-1 min-w-0 bg-slate-50 dark:bg-slate-900 relative">
              <Topbar />
              <main className="flex-1 overflow-y-auto p-6 relative">
                {children}
              </main>
            </div>
          </div>
          <Toaster richColors position="top-right" />
        </Providers>
        </ThemeProvider>
      </body>
    </html>
  );
}
