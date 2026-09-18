import urllib.request

urls = [
    'http://localhost:5121/_content/MudBlazor/MudBlazor.min.css',
    'http://localhost:5121/_content/MudBlazor/MudBlazor.min.js',
    'http://localhost:5121/_framework/blazor.web.js',
    'http://localhost:5121/Nsdms.Web.styles.css',
    'http://localhost:5121/app.css'
]

for u in urls:
    try:
        req = urllib.request.Request(u)
        with urllib.request.urlopen(req) as resp:
            data = resp.read()
            ct = resp.headers.get('Content-Type')
            print(f"OK {resp.status} | {ct} | {len(data)} bytes | {u}")
    except Exception as ex:
        print(f"FAILED | {ex} | {u}")
