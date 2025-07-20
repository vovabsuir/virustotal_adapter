!include "MUI2.nsh"
!include "LogicLib.nsh"
!include "FileFunc.nsh"
!include "WinCore.nsh"
!include "nsDialogs.nsh"

Name "VirusTotal Adapter"
OutFile "VirusTotalAdapterSetup.exe"
InstallDir "$PROGRAMFILES\VirusTotalAdapter"
ShowInstDetails show
RequestExecutionLevel admin

!define MUI_ICON "icon.ico"
!define MUI_WELCOMEPAGE_TITLE "VirusTotal Adapter Setup"
!define MUI_WELCOMEPAGE_TEXT "This wizard will guide you through the installation of VirusTotal Adapter.$\r$\n$\r$\nClick Next to continue."

!define MUI_FINISHPAGE_TITLE "Installation Complete"
!define MUI_FINISHPAGE_TEXT "VirusTotal Adapter has been successfully installed on your computer.$\r$\n$\r$\nClick Finish to close this wizard."

!define MUI_ABORTWARNING

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
Page custom ApiKeyPage ApiKeyPageLeave
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

Var ApiKeyInput
Var ApiKey

Function ApiKeyPage
  nsDialogs::Create 1018
  Pop $0
  
  ${NSD_CreateLabel} 0 0 100% 24u "VirusTotal API key is required for the application to work.$\n$\nEnter your VirusTotal API key:"
  Pop $0
  
  ${NSD_CreateText} 0 30u 100% 12u ""
  Pop $ApiKeyInput
  
  nsDialogs::Show
FunctionEnd

Function ApiKeyPageLeave
  ${NSD_GetText} $ApiKeyInput $ApiKey
  ${If} $ApiKey == ""
    MessageBox MB_ICONEXCLAMATION|MB_OK "API key cannot be empty!"
    Abort
  ${EndIf}
  WriteRegExpandStr HKCU "Environment" "VIRUS_TOTAL_API_KEY" "$ApiKey"
  SendMessage ${HWND_BROADCAST} ${WM_WININICHANGE} 0 "STR:Environment"
FunctionEnd

Section "Main Section" SEC01
  SetOutPath "$INSTDIR"

  File "VirusTotalAdapter.jar"
  File "icon.ico"
  File "run.bat"

  SetOutPath "$INSTDIR\jre"
  File /r "jre\*.*"

  WriteUninstaller "$INSTDIR\uninstall.exe"

  WriteRegStr HKCR "*\shell\VirusTotalScan" "" "Scan with VirusTotal"
  WriteRegStr HKCR "*\shell\VirusTotalScan\command" "" '"$INSTDIR\run.bat" "%V"'
  WriteRegStr HKCR "*\shell\VirusTotalScan" "Icon" '"$INSTDIR\icon.ico"'
  
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\VirusTotalAdapter" \
                   "DisplayName" "VirusTotal Adapter"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\VirusTotalAdapter" \
                   "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\VirusTotalAdapter" \
                   "DisplayIcon" '"$INSTDIR\icon.ico"'
SectionEnd

Section "Uninstall"
  DeleteRegKey HKCR "*\shell\VirusTotalScan"
  
  Delete "$INSTDIR\VirusTotalAdapter.jar"
  Delete "$INSTDIR\icon.ico"
  Delete "$INSTDIR\run.bat"
  Delete "$INSTDIR\uninstall.exe"
  RMDir /r "$INSTDIR\jre"
  RMDir "$INSTDIR"
  
  DeleteRegValue HKCU "Environment" "VIRUS_TOTAL_API_KEY"
  SendMessage ${HWND_BROADCAST} ${WM_WININICHANGE} 0 "STR:Environment"
  
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\VirusTotalAdapter"
SectionEnd