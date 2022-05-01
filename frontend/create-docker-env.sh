printf "export const environment = {\n" >> environment.docker.ts
printf "production: false,\n" >> environment.docker.ts
printf "tileservice: '${TILESERVICE}',\n" >> environment.docker.ts
printf "baseApiPath: '${BASEAPIPATH}',\n" >> environment.docker.ts
printf "tileKey: '${MAPAPIKEY}'\n" >> environment.docker.ts
printf "};\n" >> environment.docker.ts

mv environment.docker.ts CapybaraApp/src/environments/environment.docker.ts