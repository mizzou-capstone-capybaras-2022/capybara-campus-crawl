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
1. Create an account on https://openrouteservice.org/
2. Create and copy an api token on openrouteservice
3. Navigate to `/backend/docker-config`
5. Copy `openrouteservice-ex.properties` to `openrouteservice.properties`
6. Replace the dummy apikey in the `openrouteservice.properties` file and use the api token from openrouteservice and set `openrouteservice.active` equal to `true`

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

# Running the Map Tile server
1. `docker volume create capy-openstreetmap-data`
2. `docker run -v $PWD/MapData/missouri.pbf:/data.osm.pbf -v capy-openstreetmap-data:/var/lib/postgresql/12/main overv/openstreetmap-tile-server:1.3.10 import`
3. `docker compose -f docker-compose-map.yml up`