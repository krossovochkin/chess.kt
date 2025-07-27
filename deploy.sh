#!/bin/bash

set -e

GITHUB_TOKEN="${1}"
GITHUB_REPO="krossovochkin/personal-website"
GITHUB_BRANCH="master"

printf "\033[0;32mClean up website folder...\033[0m\n"
cd website
git checkout ${GITHUB_BRANCH}
git pull origin ${GITHUB_BRANCH}
shopt -s extglob

cd static/applications
rm -r website/static/applications/chess.kt/* || true

cd ../../..
cp -a composeApp/build/dist/wasmJs/productionExecutable/. website/static/applications/chess.kt

printf "\033[0;32mDeploying updates to GitHub...\033[0m\n"
cd website

MESSAGE="update chess.kt app $(date)"
git add .

if [[ -z "${GITHUB_TOKEN}" || -z "${GITHUB_REPO}" ]]; then
  git commit -m "$MESSAGE"
  git push origin "${GITHUB_BRANCH}"
else
  echo "Start commit"
  git config --global user.email "ci@github"
  git config --global user.name "GitHub Actions CI"
  
  git commit -m "$MESSAGE"
  
  git push --quiet "https://krossovochkin:${GITHUB_TOKEN}@github.com/${GITHUB_REPO}.git" "${GITHUB_BRANCH}"
fi
