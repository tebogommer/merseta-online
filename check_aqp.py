import pyodbc
conn = pyodbc.connect('DRIVER={ODBC Driver 18 for SQL Server};SERVER=localhost\\SQLEXPRESS;DATABASE=NSDMS-NET;UID=NSDMS-NET;PWD=NSDMS-NET;TrustServerCertificate=yes;')
cursor = conn.cursor()
cursor.execute("SELECT Id, AqpName, AqpCode FROM AqpPartner")
print('AqpPartner rows:', cursor.fetchall())
