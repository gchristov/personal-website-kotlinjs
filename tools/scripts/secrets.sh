#!/bin/bash
set -e
# Exports required CI environment secrets to local secrets so that the project can use them.
# Should be invoked from the root of the project as all paths are relative.

# Per-service infra GCP credentials
services=($(bash tools/scripts/list_services.sh))
for service in "${services[@]}"; do
    echo "$GCP_SA_KEY_INFRA" >> "$service/infra/credentials-gcp-infra.json"
done
