FROM node:lts AS build
WORKDIR /my-app/
COPY package.json src/index.js /my-app/
RUN ["npm", "install", "--production"]

FROM node:lts-alpine
WORKDIR /my-app/
COPY --from=build /my-app/ /my-app/
EXPOSE 3000
CMD ["npm", "run", "start"]