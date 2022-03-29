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

Instructions on how to run the docker containers can be found [here](instructions.md)

# Running the Map Tile server
1. `docker volume create capy-openstreetmap-data`
2. `docker run -v $PWD/MapData/missouri.pbf:/data.osm.pbf -v capy-openstreetmap-data:/var/lib/postgresql/12/main overv/openstreetmap-tile-server:1.3.10 import`
3. `docker compose -f docker-compose-map.yml up`
