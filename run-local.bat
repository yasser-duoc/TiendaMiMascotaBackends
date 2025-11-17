@echo off
echo Starting TiendaMiMascota backend (local profile)...
set "SPRING_PROFILES_ACTIVE=local"
echo SPRING_PROFILES_ACTIVE=%SPRING_PROFILES_ACTIVE%
mvn -DskipTests spring-boot:run
