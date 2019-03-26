https://www.ory.sh/run-oauth2-server-open-source-api-security


```
 docker network create hydraguide
 
```


```
 
 docker run --network hydraguide \
   --name ory-hydra-example--postgres \
   -e POSTGRES_USER=hydra \
   -e POSTGRES_PASSWORD=secret \
   -e POSTGRES_DB=hydra \
   -d postgres:9.6
   
```


```
   export SYSTEM_SECRET=y82XL-wAPCCZu+B4
```

   

```
   export DATABASE_URL=postgres://hydra:secret@ory-hydra-example--postgres:5432/hydra?sslmode=disable
```



```
docker run -it --rm \
  --network hydraguide \
  oryd/hydra:v1.0.0-beta.8 \
  migrate sql $DATABASE_URL
```

  
 ``` 
  docker run -d \
    --name ory-hydra-example--hydra \
    --network hydraguide \
    -p 9000:4444 \
    -p 9001:4445 \
    -e SYSTEM_SECRET=$SYSTEM_SECRET \
    -e DATABASE_URL=$DATABASE_URL \
    -e OAUTH2_ISSUER_URL=http://127.0.0.1:9000/ \
    -e OAUTH2_CONSENT_URL=http://127.0.0.1:9020/consent \
    -e OAUTH2_LOGIN_URL=http://127.0.0.1:9020/login \
    oryd/hydra:v1.0.0-beta.8 serve all --dangerous-force-http
```
    
    
```
docker run --rm -it \
      --network hydraguide \
      oryd/hydra:v1.0.0-beta.8 \
      clients create \
        --endpoint http://ory-hydra-example--hydra:4445 \
        --id some-consumer \
        --secret some-secret \
        --grant-types client_credentials \
        --response-types token,code
```

```
docker run -d \
  --name ory-hydra-example--consent \
  -p 9020:3000 \
  --network hydraguide \
  -e HYDRA_URL=http://ory-hydra-example--hydra:4445 \
  -e NODE_TLS_REJECT_UNAUTHORIZED=0 \
  oryd/hydra-login-consent-node:v1.0.0-beta.8
  ```
  
```
docker run --rm -it \
  --network hydraguide \
  oryd/hydra:v1.0.0-beta.8 \
  clients create \
    --endpoint http://ory-hydra-example--hydra:4445 \
    --id another-consumer \
    --secret consumer-secret \
    -g authorization_code,refresh_token \
    -r token,code,id_token \
    --scope openid,offline \
    --callbacks http://localhost:8080/login/oauth2/code/my-client-1
```


