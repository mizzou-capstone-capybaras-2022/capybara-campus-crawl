# Running the Project

# Configuration
1. create a .env file in the root directory

```
ORS_KEY=
DATABASE_STRING=
DATABASE_USER=
DATABASE_PASSWD=
MAPAPIKEY=
BASEAPIPATH=
```

2. Fill these keys with database and OpenRouteServie Api key and Map Api Key

For steps 3-5
Make sure you are in root directory of the capstone project.

3. Build the frontend container
```
docker compose -f docker-compose-frontend.yml build
```

4. Build the backend container
```
docker compose -f docker-compose-backend.yml build
```
or linux
```
docker-compose -f docker-compose-backend.yml build
```

5. Startup the frontend and the backend
```
docker compose -f docker-compose-frontend.yml -f docker-compose-backend.yml up
```
or linux
```
docker-compose -f docker-compose-frontend.yml -f docker-compose-backend.yml up
```

6. Navigate to Swagger UI
```
http://localhost:8090/swagger-ui/index.html
```

7. Tear down the docker compose
```
docker compose -f docker-compose-frontend.yml -f docker-compose-backend.yml down
```
or linux
```
docker-compose -f docker-compose-frontend.yml -f docker-compose-backend.yml down


## Alternatively if just running the backend in docker. Do the following.
1. Run the backend
```
docker compose -f docker-compose-backend.yml up
```
2. In a separate terminal do the following
3. change to the main frontend directory `/frontend/CapybaraApp`
4. Make sure you replace the `tileKey` with the MapTiler api key in `/frontend/CapybaraApp/environment/environment.ts`
5. run `ng serve`

## VMMEM issue
1. https://ryanharrison.co.uk/2021/05/13/wsl2-better-managing-system-resources.html#:~:text=Setting%20a%20WSL2%20Memory%20Limit,wslconfig%20).

2. Pull up command prompt and run
```
wsl --shutdown
```
3. Restart the Docker Desktop Program
