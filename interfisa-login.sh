#!/bin/bash

ENDPOINT_LOGIN="/api/auth/login"
ENDPOINT_LOGIN_V2="/api/auth/v2/login"
ENDPOINT_REFRESH="/api/auth/refresh"
DEFAULT_BASE_URL="http://localhost:8090"

MODE="oauth2"
BASE_URL="${BASE_URL:-$DEFAULT_BASE_URL}"
for arg in "$@"; do
  case $arg in
    --mode=legacy) MODE="legacy" ;;
    http*) BASE_URL="$arg" ;;
  esac
done

if [ "$MODE" = "legacy" ]; then
  URL_TARGET="$BASE_URL$ENDPOINT_LOGIN"
  curl -s -X 'POST' "$URL_TARGET" \
    -H 'accept: application/json' \
    -H 'X-INTERFISA-BE-VERSION: V1.0' \
    -H 'Content-Type: application/json' \
    -d '{ "banca": "E", "ruc": "80026021-0", "ci": "3497824", "password": "123456", "canal": "AM", "deviceId": "string" }'
else
  URL_LOGIN="$BASE_URL$ENDPOINT_LOGIN_V2"
  URL_REFRESH="$BASE_URL$ENDPOINT_REFRESH"
  LOGIN_RESPONSE=$(curl -s -X 'POST' "$URL_LOGIN" \
    -H 'accept: application/json' \
    -H 'X-INTERFISA-BE-VERSION: V1.0' \
    -H 'Content-Type: application/json' \
    -d '{ "banca": "E", "ruc": "80026021-0", "ci": "3497824", "password": "123456", "canal": "AM", "deviceId": "string" }')

  REFRESH_TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"refreshToken"[ ]*:[ ]*"[^"]*"' | sed 's/.*: *"\([^"]*\)"/\1/')

  if [ -z "$REFRESH_TOKEN" ]; then
    echo "No se pudo obtener refreshToken. Respuesta:"
    echo "$LOGIN_RESPONSE"
    exit 1
  fi

  REFRESH_RESPONSE=$(curl -s -X 'POST' "$URL_REFRESH" \
    -H 'accept: application/json' \
    -H 'X-INTERFISA-BE-VERSION: V1.0' \
    -H 'Content-Type: application/json' \
    -d "{ \"refreshToken\": \"$REFRESH_TOKEN\" }")

  ACCESS_TOKEN=$(echo "$REFRESH_RESPONSE" | grep -o '"accessToken"[ ]*:[ ]*"[^"]*"' | sed 's/.*: *"\([^"]*\)"/\1/')

  echo "refreshToken: $REFRESH_TOKEN"
  echo "accessToken:  $ACCESS_TOKEN"
fi
