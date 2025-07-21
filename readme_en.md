<div align="center">
  
# 🛡️ VirusTotal Adapter

![Java](https://img.shields.io/badge/Java-24-orange?logo=openjdk)
![License](https://img.shields.io/badge/license-MIT-blue)

**File analysis via VirusTotal directly from the Explorer context menu**

[📖 Documentation](docs/project_info_en.md) | [🛠 Installation](#-установка) | [🤝 Contributing](#-contributing)

</div>

## 🔍 Review
A utility for scanning files with VirusTotal in just 3 clicks. Features:
- ✅ Scanning through Explorer context menu
- 🚀 Built-in JRE (no need to install Java)
- 📊 Detailed scanning statistics
- ⚡ Support for files up to 650 Mb (see [VirusTotal Specification](docs/virustotal_spec_en.md))

## 💾 Installation
1. Download `VirusTotalAdapterSetup.exe`  
  [![NSIS](https://img.shields.io/badge/Setup-.exe-blue?logo=NSIS&style=for-the-badge)](installer/VirusTotalAdapterSetup.exe)
2. Run it and follow the instuctions

A detailed installation guide is avalaible at 📘 [Installation Guide](docs/installation_guide_en.md)

## 🛠 Technologies
[![Java 24](https://img.shields.io/badge/Java-24-%23ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![VirusTotal API](https://img.shields.io/badge/VirusTotal-API_v3-%233776AB?logo=virustotal)](https://developers.virustotal.com/)
[![NSIS](https://img.shields.io/badge/Installer-NSIS-%2300599C?logo=windows)](https://nsis.sourceforge.io/)

<details>
<summary>📦 Dependency list</summary>

- **HTTP-client**: `java.net.http`
- **JSON**: `Jackson 2.19.1`
- **Code generation**: `Lombok 1.18.38`
- **Build tool**: `Maven Assembly Plugin 3.7.0`
</details>

## 💬 Support
#### Found a bug or have a suggestion?  
- Open an <b>[issue](https://github.com/vovabsuir/test/issues)</b> or email <b>vovabsuir@gmail.com</b>
- Or connect via Telegram [![Telegram](https://img.shields.io/badge/Chat-Telegram-blue?logo=telegram)](https://t.me/+76LBDzoK2xlmNzUy)

## 🤝 Contributing
1. Fork the repository
2. Create a new branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push and create PR: `git push origin feature/your-feature`

📌 Full manual: [CONTRIBUTING.md](.github/contributing_en.md)

## 📋 Roadmap
- [ ] GUI-analogue to expand functionality
- [ ] Localization support for DE, ES, FR
- [ ] Auto-updating mechanism
