import pyodbc
conn = pyodbc.connect('DRIVER={ODBC Driver 18 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET;TrustServerCertificate=yes;')
cursor = conn.cursor()
for tbl in ['DocumentTemplate', 'DocumentTemplateSection', 'DocumentClause']:
    cursor.execute(f"SELECT COUNT(*) FROM sys.tables WHERE name = '{tbl}'")
    exists = cursor.fetchone()[0]
    print(f"Table {tbl} exists:", bool(exists))
    if exists:
        cursor.execute(f"SELECT COUNT(*) FROM [{tbl}]")
        print(f"  Count in {tbl}:", cursor.fetchone()[0])
