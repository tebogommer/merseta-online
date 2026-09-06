import pyodbc

conn = pyodbc.connect('DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET')
cursor = conn.cursor()

cursor.execute("SELECT * FROM dbo.LearnerTradeTestApplication")
cols = [col[0] for col in cursor.description]
rows = cursor.fetchall()
print(f"Total rows: {len(rows)}")
for r in rows:
    print(f"\n--- Row Id: {r[0]} ---")
    for col_name, val in zip(cols, r):
        if val is None:
            print(f"  {col_name}: NULL")

conn.close()
