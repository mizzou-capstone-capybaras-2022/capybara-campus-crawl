#stage 1
FROM node:lts-alpine as node

ARG TILESERVICE=http://{s}.tile.openstreetmap.org
ARG BASEAPIPATH=http://localhost:8090

WORKDIR /app

COPY ./CapybaraApp .

RUN printf "export const environment = {\n" >> environment.docker.ts
RUN printf "production: false,\n" >> environment.docker.ts
RUN printf "tileservice: '${TILESERVICE}',\n" >> environment.docker.ts
RUN printf "baseApiPath: '${BASEAPIPATH}'\n" >> environment.docker.ts
RUN printf "};\n" >> environment.docker.ts

RUN cat environment.docker.ts

RUN mv environment.docker.ts /app/src/environments/environment.docker.ts

RUN npm install
RUN npm run build -- --configuration=docker

#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/capybara-app /usr/share/nginx/html