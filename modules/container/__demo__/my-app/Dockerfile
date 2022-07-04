FROM node:lts AS build
WORKDIR /my-app/
COPY ./package.json ./index.js /my-app/
RUN ["npm", "install", "--production"]

FROM node:lts-alpine
WORKDIR /my-app/
COPY --from=build /my-app/ /my-app/
EXPOSE 8080
CMD ["npm", "run", "start"]