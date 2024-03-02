FROM nginx:1.25.3

ARG LANDING_PAGE_WEB_SERVICE_HOST
# Need to forward the nginx env variables manually for production. Locally we use Docker Compose which does this automatically.
ENV LANDING_PAGE_WEB_SERVICE_HOST=${LANDING_PAGE_WEB_SERVICE_HOST}

COPY ./default.conf.template /etc/nginx/templates/default.conf.template
