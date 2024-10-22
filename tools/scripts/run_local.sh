# Builds and runs the project locally with Docker
set -e
echo "🛠 Build project" && ./gradlew assemble
echo "🧹 Clean up old Docker resources" && (docker image prune -af)
echo "🏁 Start app" && echo "" && docker compose \
-f tools/docker/landing-page-web-compose.yaml \
-f tools/docker/proxy-web-compose.yaml \
up --build --remove-orphans