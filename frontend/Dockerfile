#stage 1
FROM node:lts-alpine as node
ARG CONFIGURATION=development

WORKDIR /app
COPY ./CapybaraApp .
RUN npm install
RUN npm run build -- --configuration=${CONFIGURATION}

#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/capybara-app /usr/share/nginx/html