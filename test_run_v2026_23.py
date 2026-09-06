import pyodbc
import os

conn_str = "DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET"
conn = pyodbc.connect(conn_str, autocommit=True)
cursor = conn.cursor()

script_file = r"c:\Antigravity\nsdms-2026-04-01\nsdms\MerSETA\dotnet\Nsdms.Infrastructure\Data\SqlScripts\V2026_23_WorkplaceApproval_Spec_Alignment.sql"
print(f"Reading {script_file}...")
with open(script_file, "r", encoding="utf-8") as f:
    sql = f.read()

# Split on GO
batches = [b.strip() for b in sql.split("GO") if b.strip()]
print(f"Found {len(batches)} batches.")

for i, b in enumerate(batches):
    try:
        cursor.execute(b)
        print(f"Batch {i+1} executed successfully.")
    except Exception as ex:
        print(f"Batch {i+1} failed: {ex}")

conn.close()
