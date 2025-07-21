<div align="center">
  
# 🛡️ VirusTotal Adapter

![Java](https://img.shields.io/badge/Java-24-orange?logo=openjdk)
![License](https://img.shields.io/badge/license-MIT-blue)

**Анализ файлов через VirusTotal прямо из контекстного меню Проводника**

[📖 Документация](docs/project_info_ru.md) | [🛠 Установка](#-установка) | [🤝 Contributing](#-contributing)

</div>

## 🔍 Обзор
Утилита для проверки файлов через VirusTotal в 3 клика. Особенности:
- ✅ Сканирование через контекстное меню Проводника
- 🚀 Встроенная JRE (не требует установки Java)
- 📊 Детальная статистика сканирования
- ⚡ Поддержка файлов до 650 МБ (смотрите [Спецификация VirusTotal](docs/virustotal_spec_ru.md))

## 💾 Установка
1. Скачайте файл `VirusTotalAdapterSetup.exe`  
  [![NSIS](https://img.shields.io/badge/Setup-.exe-blue?logo=NSIS&style=for-the-badge)](installer/VirusTotalAdapterSetup.exe)
2. Запустите его и следуйте инструкциям

Подробное описание процесса установки изложено в 📘 [Installation Guide](docs/installation_guide_ru.md)

## 🛠 Технологии
[![Java 24](https://img.shields.io/badge/Java-24-%23ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![VirusTotal API](https://img.shields.io/badge/VirusTotal-API_v3-%233776AB?logo=virustotal)](https://developers.virustotal.com/)
[![NSIS](https://img.shields.io/badge/Installer-NSIS-%2300599C?logo=windows)](https://nsis.sourceforge.io/)

<details>
<summary>📦 Полный список зависимостей</summary>

- **HTTP-клиент**: `java.net.http`
- **JSON**: `Jackson 2.19.1`
- **Генерация кода**: `Lombok 1.18.38`
- **Сборка**: `Maven Assembly Plugin 3.7.0`
</details>

## 💬 Поддержка
#### Нашли баг или есть предложение?  
- Откройте <b>[issue](https://github.com/vovabsuir/test/issues)</b> или напишите на <b>vovabsuir@gmail.com</b>
- Или свяжитесь через телеграмм [![Telegram](https://img.shields.io/badge/Chat-Telegram-blue?logo=telegram)](https://t.me/+76LBDzoK2xlmNzUy)

## 🤝 Contributing
1. Форкните репозиторий
2. Создайте ветку: `git checkout -b feature/your-feature`
3. Сделайте commit: `git commit -m 'Add some feature'`
4. Отправьте PR: `git push origin feature/your-feature`

📌 Полное руководство: [CONTRIBUTING.md](.github/contributing_ru.md)

## 📋 Roadmap
- [ ] GUI-аналог для расширения функционала   
- [ ] Локализация DE, ES, FR
- [ ] Авто-обновление
