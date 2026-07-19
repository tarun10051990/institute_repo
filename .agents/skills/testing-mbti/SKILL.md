---
name: testing-mbti
description: End-to-end test the MBTI Personality Test feature (and general Career Assessment Platform setup) in institute_repo. Use when verifying the /mbti UI flow, the MBTI backend endpoints, or bringing the app up locally for testing.
---

# Testing the MBTI Personality Test

## What the feature does
Public, stateless MBTI test. User answers 20 forced-choice questions across 4 dichotomies
(EI, SN, TF, JP); the app scores a 4-letter type and renders a detailed report.
- Frontend route: `/mbti` (navbar link "MBTI Test"), component `frontend/src/app/components/mbti-test/`.
- Backend: `GET /api/mbti/questions`, `POST /api/mbti/result` (no auth). `MbtiController` / `MbtiService`.

## Bringing the app up locally (required before UI testing)
Node must be >= 20.19 — the repo's default may be too old; use nvm:
```bash
source ~/.nvm/nvm.sh && nvm use 22.12.0   # or any >=22.12
```
MySQL is needed for the backend to start (it validates the schema even though MBTI itself
doesn't touch the DB). If not present:
```bash
sudo apt-get install -y mysql-server && sudo service mysql start
# The schema does NOT create the app user — create it manually:
sudo mysql -e "CREATE USER IF NOT EXISTS 'career_app'@'localhost' IDENTIFIED WITH mysql_native_password BY 'career_pass_123';
CREATE DATABASE IF NOT EXISTS career_assessment_db;
GRANT ALL PRIVILEGES ON career_assessment_db.* TO 'career_app'@'localhost'; FLUSH PRIVILEGES;"
sudo mysql career_assessment_db < database/schema.sql
sudo mysql career_assessment_db < database/seed_data.sql
```
Run servers (backend :8080, frontend :4200):
```bash
(cd backend && nohup mvn spring-boot:run > /tmp/backend.log 2>&1 &)
(cd frontend && source ~/.nvm/nvm.sh && nvm use 22.12.0 && nohup npx ng serve > /tmp/frontend.log 2>&1 &)
```
Check readiness with `ss -ltnp | grep -E ':(8080|4200)'` before starting duplicates.

## Fast backend verification (do this first — cheap and deterministic)
```bash
curl -s http://localhost:8080/api/mbti/questions   # expect 20 questions, dims {EI,SN,TF,JP}
```
Deterministic result checks (option A of each question maps to E/S/T/J; option B to I/N/F/P):
- All first-letter answers (E,S,T,J) → type **ESTJ** ("The Executive"), every dim 100%.
- All second-letter answers (I,N,F,P) → type **INFP** ("The Mediator"), every dim 100%.
Mixed answers verify count-based scoring + strength % (e.g. T×3/F×2 → T@60%).

## UI flow to record
1. Navbar "MBTI Test" → intro card (title + 4 dichotomies + "Start the Test").
2. Start → Q1, "Question 1 of 20", 5% bar, 20 dot-nav. Selecting an option **auto-advances**.
3. Pick a full deterministic pattern (all option A, or all option B) to get a known type.
4. IMPORTANT: the **last question does not auto-advance** — you must click its option, then the
   button changes from "Next" to "See My Result". If it still says "Next" (disabled), Q20 isn't
   answered yet. When batching clicks, remember N clicks answer N questions and advance N-1 times.
5. Click "See My Result" → verify report: type code, nickname, overview, 4 preference bars with
   %, strengths, blind spots, career chips, relationships, growth tips.
6. Run a second pass with the opposite answers ("Retake Test") to prove the type actually changes
   (guards against a hardcoded/broken scorer). Note: Retake reloads the intro; the page may be
   scrolled — screenshot before clicking "Start".

## Gotchas
- No CI is configured on this repo — manual testing is the only gate.
- Option buttons are at different y-coordinates depending on page scroll position; screenshot to
  confirm the option location rather than assuming fixed coordinates.

## Devin Secrets Needed
None. Local DB creds are hardcoded in `backend/src/main/resources/application.properties`
(`career_app` / `career_pass_123`) and the MBTI feature needs no external credentials.
