export default function PDFLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className="bg-white m-0 p-0 antialiased print:bg-white print:m-0 print:p-0">
        {/* We strip out all navigation and sidebars for clean, printable layouts */}
        {children}
      </body>
    </html>
  )
}
