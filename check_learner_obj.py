import pyodbc

conn = pyodbc.connect('DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET')
cursor = conn.cursor()
cursor.execute("SELECT name, type_desc FROM sys.objects WHERE name LIKE '%Learner%'")
for r in cursor.fetchall():
    print(r)
conn.close()
