import pyodbc
conn = pyodbc.connect('DRIVER={ODBC Driver 18 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET;TrustServerCertificate=yes;')
cursor = conn.cursor()
cursor.execute("SELECT name FROM sys.indexes WHERE object_id = OBJECT_ID('GrantApplication') AND name LIKE '%FundingWindow%'")
print('Indexes on FundingWindow:', cursor.fetchall())

cursor.execute("SELECT name FROM sys.indexes WHERE object_id = OBJECT_ID('GrantApplication')")
print('All Indexes on GrantApplication:', [r[0] for r in cursor.fetchall()])
