(function () {
    const stored = localStorage.getItem('nsdms_dark_mode') || localStorage.getItem('app_theme') || 'system';
    const dark = stored === 'dark' || stored === 'true' || (stored === 'system' && window.matchMedia('(prefers-color-scheme: dark)').matches);
    document.documentElement.setAttribute('data-theme', dark ? 'dark' : 'light');
    if (dark) document.documentElement.classList.add('mud-theme-dark');
    else document.documentElement.classList.remove('mud-theme-dark');
})();
