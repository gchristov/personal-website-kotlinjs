#!/bin/bash
set -e
# Exports required CI environment secrets to local secrets so that the project can use them

# GCP credentials
directories=(
  "common"
  "landing-page-web"
  "proxy-web"
)
for dir in "${directories[@]}"; do
  echo "$GCP_SA_KEY_INFRA" >> "$dir/infra/credentials-gcp-infra.json"
done