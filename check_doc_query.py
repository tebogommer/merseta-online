import pyodbc
conn = pyodbc.connect('DRIVER={ODBC Driver 18 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET;TrustServerCertificate=yes;')
cursor = conn.cursor()
cursor.execute("""
SELECT d.[Id], d.[TemplateTitle], d.[ParentTemplateId]
FROM [DocumentTemplate] AS d
WHERE d.[Id] = 1
""")
print('Direct query for Id=1:', cursor.fetchall())
