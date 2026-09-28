# Task Manager

En enkel uppgiftshanterare med REST-API i Spring Boot och en vanilla HTML/CSS/JS-frontend.
Data lagras i minnet (ingen databas) — listan nollställs varje gång appen startas om.

**Live:**
- Dev: https://task-manager-dev-tjg3.onrender.com/
- Prod: https://task-manager-prod.onrender.com/

> Tjänsterna körs på Renders gratisnivå och kan behöva ~30 sekunder på sig att vakna
> vid första anropet om de varit inaktiva.

## Vad appen gör

Lista, lägg till, markera som klar och ta bort uppgifter via ett webbgränssnitt som pratar
med ett REST-API.

| Metod  | Endpoint      | Gör                              |
|--------|---------------|-----------------------------------|
| GET    | /tasks        | Lista alla uppgifter             |
| POST   | /tasks        | Skapa uppgift                    |
| PATCH  | /tasks/{id}   | Ändra namn eller markera klar    |
| DELETE | /tasks/{id}   | Ta bort uppgift                  |

Tre affärsregler testas automatiskt:
- Namn under 2 tecken → 400
- Uppgift som inte finns → 404
- Uppgift som redan är klar kan inte markeras klar igen → 409

## Köra lokalt

Kräver Java 17 och Maven.

```
git clone https://github.com/MuamerBrankovic/task-manager-cicd.git
cd task-manager-cicd
mvn spring-boot:run
```

Appen och frontenden nås på http://localhost:8080 (Spring Boot serverar frontenden som
statiska filer, så det är samma adress för både API och gränssnitt).

### Köra testerna

```
mvn test
```

kör enhets- och integrationstesterna.

E2E-testerna (Playwright) ligger i en egen mapp och kräver att appen är igång:

```
cd e2e
npm install
npx playwright install
npx playwright test
```

## CI/CD-flödet

Allt arbete sker på feature-brancher mot `dev`, aldrig direkt på `dev` eller `main`.
Varje Pull Request kör hela testsviten innan den får mergas.

**Vid PR mot `dev`:**
- `tests.yml` bygger projektet och kör enhets- och integrationstester
- `e2e.yml` startar appen, väntar tills den svarar, och kör E2E-testerna med Playwright i tre
  webbläsare (chromium, firefox, webkit)

**Vid push (merge) till `dev`:**
- `docker-dev.yml` bygger en Docker-image, pushar den till DockerHub, och triggar en deploy
  till dev-miljön på Render

**Vid push (merge) till `main`:**
- `docker-prod.yml` bygger en egen Docker-image, pushar den till DockerHub, och triggar en
  separat deploy till prod-miljön på Render

Dev och prod är alltså två helt separata miljöer med egna Docker-images och egna
deploy-hooks — en ändring i dev-miljön påverkar aldrig prod förrän den mergats vidare
till `main`.

## Teknikstack

- Backend: Java 17, Spring Boot 4.0.2
- Frontend: HTML, CSS, vanilla JavaScript (fetch API)
- Test: JUnit 5, Mockito, MockMvc, Playwright
- CI/CD: GitHub Actions
- Deploy: Docker, DockerHub, Render
