# TICKET TEAM

## GitHub
Repository can be cloned from [https://github.com/CitadelCS/ticket-team](https://github.com/CitadelCS/ticket-team)

Current Project Board can be found [https://github.com/CitadelCS/ticket-team/projects](https://github.com/CitadelCS/ticket-team/projects)

GitHub Actions can be found [https://github.com/CitadelCS/ticket-team/actions](https://github.com/CitadelCS/ticket-team/actions)

## Deployment
The [https://ticket-team-frontend.herokuapp.com/](https://ticket-team-frontend-712588ea25f5.herokuapp.com/) and [https://ticket-team-api.herokuapp.com/](https://ticket-team-api-1b1845229c6d.herokuapp.com/) are deployed via Heroku. They will be automatically deploy via GitHub on a push to the `master` branch (if it passes testing). 

## Git Standards
We have a "prod" and "non-prod" environment / branches

`master` is our production branch, "releases" should occur in the form of a pull request from the `development` branch.

When working on a new feature, branch from `development` and utilize the following naming convention:

`feature/{initials}/{feature-description}`

When working on a bugfix, branch from `development` and utilize the following naming convention:
`bugfix/{initials}/{bug-description}`

## Code Infrastructure

The ticket team consists of a seperate API and UI.

The API is a Java / Spring Boot application that utilizes various third-party APIs to bring relevant event/ticketing data.

The UI is a basic NextJs client side application that utilize the API to return relevant information to users.

## Environment Setup

1.) Install Java JDK 17+. JDK located [here](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html). If you have a Mac you can use `brew`.

```bash
brew install --cask corretto@17
```

If you're on Windows, download the `.msi` installer from [here](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html) and run.

2.) Install Node.js (version 12.0 or later is recommended). You can download it from [here](https://nodejs.org/)

```bash
brew install node
```

3.) You will need access to PostgreSQL Database Credentials & Third Party API keys. These are currently stored in GitHub Secrets so on deployment they will be available.
Dr. Ravan if you read this line you can fail us.

## Build / Run The API

> Disclaimer: If running on a Windows machine replace `./mvnw` with `.\mvnw`

1.) Build the API

From the root directory:

```bash
./mvnw clean install
```

2.) Run the API

```bash
./mvnw spring-boot:run
```

Access the API by visiting [http://localhost:5001/swagger-ui/index.html](http://localhost:5001/swagger-ui/index.html). Or utilize Postman, cURL or another http request tool of your liking.

## Run the UI

From the root directory:

```bash
cd frontend
npm install
npm run dev
```

Access the application: Open your browser and go to [http://localhost:3000](http://localhost:3000)