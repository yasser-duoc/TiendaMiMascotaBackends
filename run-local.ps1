$ErrorActionPreference = "Stop"
Write-Host "Starting TiendaMiMascota backend (local profile)..."
$env:SPRING_PROFILES_ACTIVE = "local"
Write-Host "SPRING_PROFILES_ACTIVE = $env:SPRING_PROFILES_ACTIVE"
mvn -DskipTests spring-boot:run
