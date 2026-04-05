#!/usr/bin/env bash
set -euo pipefail

: "${PORT:=8080}"
: "${SPRING_PROFILES_ACTIVE:=prod}"

required_env_vars=(
  SPRING_DATASOURCE_URL
  SPRING_DATASOURCE_USERNAME
  SPRING_DATASOURCE_PASSWORD
  JWT_SECRET
  GOOGLE_CLIENT_ID
  GOOGLE_CLIENT_SECRET
  GOOGLE_REDIRECT_URI
  FRONTEND_BASE_URL
  CORS_ALLOWED_ORIGINS
)

for var_name in "${required_env_vars[@]}"; do
  if [[ -z "${!var_name:-}" ]]; then
    echo "Missing required environment variable: ${var_name}" >&2
    exit 1
  fi
done

app_jar="${APP_JAR:-}"
if [[ -z "${app_jar}" ]]; then
  app_jar="$(ls target/quantity_measurement_app-*.jar 2>/dev/null | grep -v '\.original$' | head -n 1 || true)"
fi

if [[ -z "${app_jar}" || ! -f "${app_jar}" ]]; then
  echo "Built jar not found in target/. Run: ./mvnw clean package" >&2
  exit 1
fi

exec java -Dspring.profiles.active="${SPRING_PROFILES_ACTIVE}" -Dserver.port="${PORT}" -jar "${app_jar}"
