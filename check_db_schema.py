import pyodbc

conn_str = "DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET"
try:
    conn = pyodbc.connect(conn_str)
    cursor = conn.cursor()
    print("Successfully connected to SQL Server!")

    # Check tables
    tables = [
        'WorkplaceApproval', 'LearnerTradeTest', 'LearnerRegisteredUnitStandard', 
        'SummativeAssessmentUnitStandard', 'EisaAssessmentEntry', 'AssessmentBatch',
        'AssessmentBatchLearner', 'ModerationChecklistEtqTp043', 'CertificatePrintingBatch',
        'LearnerCertificate', 'DistributionLetter'
    ]
    for tbl in tables:
        cursor.execute("SELECT 1 FROM sys.tables WHERE name = ?", (tbl,))
        row = cursor.fetchone()
        print(f"Table {tbl} exists: {bool(row)}")
        if row:
            cursor.execute("SELECT c.name, t.name as type_name FROM sys.columns c JOIN sys.types t ON c.user_type_id = t.user_type_id WHERE c.object_id = OBJECT_ID(?) ORDER BY c.column_id", (tbl,))
            cols = [f"{r[0]} ({r[1]})" for r in cursor.fetchall()]
            print(f"  Columns: {', '.join(cols[:15])} ... (Total: {len(cols)})")

    conn.close()
except Exception as ex:
    print(f"Error querying SQL Server: {ex}")
