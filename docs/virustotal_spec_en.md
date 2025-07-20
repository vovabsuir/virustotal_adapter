<div align="center">

# 📄 VirusTotal Specification

</div>


**This document outlines the key limitations and behavioral nuances of VirusTotal that affect how files are scanned**

## 🚫 Restrictions
- ⏱️ Request limits:
  - **4 requests per minute**
  - **500 requests per day**
  - **15,500 requests per month**
> Exceeding these limits results in a `QuotaExceedError`. Daily quotas reset at **00:00 UTC**
- 📁 File size:
  - Max size is **650 MB**
  - ⚠️ However, according to [VirusTotal documentation](https://docs.virustotal.com/reference/files-upload-url), files larger than **200 MB** are not recommended:
    - Some engines may timeout or skip scanning
    - You may lose context (in case of using archive with size larger than 200 Mb)
    - Inner files may be more meaningful for individual scanning

## ⚙️ Functional Notes
- 🔄 After uploading, VirusTotal assigns a unique analysis ID and returns it in the response
- ⏳ Scanning time may vary:
  - Not just based on file size, but also influenced by server load
  - Delays longer than 1 minute are possible
- 🔑 API key:
  - Currently, only **one** API key is supported in the app
  - If you encounter daily limits, feel free to [open an issue](https://github.com/vovabsuir/virustotal_adapter/issues)
  - 🎯 Support for **multiple keys** may be added in future releases
