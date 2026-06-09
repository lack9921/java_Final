param(
    [string]$Release = "17"
)

$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
$BuildDir = Join-Path $Root "build\classes"
$DistDir = Join-Path $Root "dist"
$JarFile = Join-Path $DistDir "StarMaze.jar"
$RunFile = Join-Path $DistDir "run.bat"

New-Item -ItemType Directory -Force -Path $BuildDir, $DistDir | Out-Null
Get-ChildItem -Path $BuildDir -Recurse -Force | Remove-Item -Recurse -Force

$Sources = Get-ChildItem -Path (Join-Path $Root "src\main\java") -Recurse -Filter *.java | ForEach-Object { $_.FullName }
if (-not $Sources) {
    throw "No Java sources found."
}

javac -encoding UTF-8 --release $Release -d $BuildDir $Sources
jar --create --file $JarFile --manifest (Join-Path $Root "MANIFEST.MF") -C $BuildDir .
Set-Content -Encoding ASCII -Path $RunFile -Value @(
    "@echo off",
    "cd /d ""%~dp0""",
    "java -jar StarMaze.jar",
    "pause"
)

Write-Host "Built $JarFile"
Write-Host "Run with: java -jar `"$JarFile`""
