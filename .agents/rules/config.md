# Configuration & Secret Governance Rule

> Enforces externalized configuration and secure credential management.

## 1. Externalized URLs & Paths
- **Zero Hardcoded Endpoints**: All external URLs (e.g. `https://verify.merseta.org.za`, SMS portals, ERP web services) must be loaded dynamically from `ISystemConfigurationService` or `IConfiguration`. Never inline URLs in C# strings.
- **Base URL Resolution**: Dynamic QR code links, PDF download references, and email callback URLs must resolve from the configured `System.BaseUrl` key.

## 2. Secrets & Credentials
- All connection strings, passwords, and API keys must be stored in environment variables, User Secrets, or Azure Key Vault.
- Never commit plaintext credentials to `appsettings.json` or `appsettings.Development.json`.
- Integrations (Dynamics GP, Sage, Live SARS FTP, Live DHET SFTP) must default to `IsEnabled = false`.
