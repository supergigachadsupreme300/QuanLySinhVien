$root = Join-Path $PSScriptRoot ".."
$root = (Resolve-Path $root).ProviderPath
$files = Get-ChildItem -Path $root -Recurse -Filter *.java | Select-Object -ExpandProperty FullName

$allContent = @{}
foreach ($f in $files) {
    try { $allContent[$f] = Get-Content -Raw -Encoding UTF8 $f } catch { $allContent[$f] = "" }
}

$unused = New-Object System.Collections.Generic.List[string]
foreach ($f in $files) {
    $text = $allContent[$f]
    $types = @()
    foreach ($m in ([regex]::Matches($text, '\b(class|interface|enum)\s+(\w+)', 'IgnoreCase'))) {
        $types += $m.Groups[2].Value
    }
    if ($types.Count -eq 0) {
        $types = @( [System.IO.Path]::GetFileNameWithoutExtension($f) )
    }
    $used = $false
    foreach ($t in $types) {
        $pattern = "\b$t\b"
        foreach ($other in $files) {
            if ($other -eq $f) { continue }
            if ([regex]::IsMatch($allContent[$other], $pattern)) { $used = $true; break }
        }
        if ($used) { break }
    }
    if (-not $used) { $unused.Add($f) }
}

Write-Host "Found $($files.Count) Java files; $($unused.Count) appear unused (no references from other files):"
foreach ($u in $unused | Sort-Object) { Write-Host $u }
