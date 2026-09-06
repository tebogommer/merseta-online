import pyodbc

conn_str = "DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET"
conn = pyodbc.connect(conn_str, autocommit=True)
cursor = conn.cursor()

script_file = r"c:\Antigravity\nsdms-2026-04-01\nsdms\MerSETA\dotnet\Nsdms.Infrastructure\Data\SqlScripts\V2026_32_Fix_Enterprise_Schema_Defects.sql"
with open(script_file, "r", encoding="utf-8") as f:
    sql = f.read()

batches = [b.strip() for b in sql.split("GO") if b.strip()]
print(f"Executing {len(batches)} batches from V2026_32...")

for i, b in enumerate(batches):
    try:
        cursor.execute(b)
        print(f"  Batch {i+1} OK")
    except Exception as ex:
        print(f"  Batch {i+1} FAILED: {ex}")

conn.close()
print("Migration completed!")
