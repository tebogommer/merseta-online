import pyodbc

conn_str = "DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET"
conn = pyodbc.connect(conn_str)
cursor = conn.cursor()

def print_cols(table):
    cursor.execute("SELECT c.name FROM sys.columns c WHERE c.object_id = OBJECT_ID(?) ORDER BY c.column_id", (table,))
    cols = [r[0] for r in cursor.fetchall()]
    print(f"\n--- {table} ({len(cols)} columns) ---")
    print(", ".join(cols))

print_cols('WorkplaceApproval')
print_cols('LearnerTradeTest')

conn.close()
