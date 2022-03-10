# capybara-campus-crawl
The main monorepo for the Mizzou CS Senior Capstone project.

# About
TBD

# Team
- Michael Navazhylau
- Isaac Milarsky
- Olivia LaVal
- Robert Graman
- Daniel Marin
- Alex Centorbi

# Docker
1. run `docker compose build`
2. run `docker compose up`
3. You are ready to go

# Docker (with cached builds)
1. uncomment the following line
`dockerfile: cache.Dockerfile`
2. run `COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose build`
3. You are ready to go