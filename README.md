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

# Prepping Docker
1. Navigate to `/backend/docker-config`
2. Rename `openrouteservice-ex.properties` to `openrouteservice.properties`
3. Replace the dummy apikey in the `openrouteservice.properties` and set `openrouteservice.active` equal to `true`

# Docker
1. run `docker compose build`
2. run `docker compose up`
3. You are ready to go

# Docker (with cached builds)
1. uncomment the following line
`dockerfile: cache.Dockerfile`
2. run `COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose build`
3. You are ready to go

# Running Mock server
1. navigate to `/mock-server`
2. Run the following
`docker run --rm -ti -p 8080:8080 -v $PWD:/opt/imposter/config outofcoffee/imposter-openapi`
3. Navigate to `localhost:8080/_spec` to play with the mocked api
