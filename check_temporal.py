import pyodbc

conn = pyodbc.connect('DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET')
cursor = conn.cursor()
cursor.execute("SELECT name, temporal_type, temporal_type_desc, history_table_id FROM sys.tables WHERE name IN ('WorkplaceApproval', 'LearnerTradeTest', 'LearnerTradeTestApplication')")
for r in cursor.fetchall():
    print(r)

conn.close()
