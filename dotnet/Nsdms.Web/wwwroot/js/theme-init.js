window.nsdmsTheme = {
    init: function () {
        const stored = localStorage.getItem('nsdms_dark_mode') || localStorage.getItem('app_theme') || 'system';
        const dark = stored === 'dark' || stored === 'true' || (stored === 'system' && window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches);
        this.setTheme(dark ? 'dark' : 'light');
        return dark;
    },
    isDark: function () {
        const stored = localStorage.getItem('nsdms_dark_mode') || localStorage.getItem('app_theme');
        if (stored === 'dark' || stored === 'true') return true;
        if (stored === 'light' || stored === 'false') return false;
        return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    },
    setTheme: function (theme) {
        const isDark = theme === 'dark';
        document.documentElement.setAttribute('data-theme', theme);
        if (isDark) {
            document.documentElement.classList.add('mud-theme-dark');
        } else {
            document.documentElement.classList.remove('mud-theme-dark');
        }
    }
};

window.nsdmsTheme.init();

window.nsdmsDownloadFile = function (filename, base64, mimeType) {
    mimeType = mimeType || 'application/octet-stream';
    const a = document.createElement('a');
    a.href = 'data:' + mimeType + ';base64,' + base64;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
};
