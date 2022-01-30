FROM node:14.18.3-alpine AS build
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM nginx
WORKDIR /usr/share/nginx/html
COPY --from="build" /app/dist/das-angular-front .
EXPOSE 80
