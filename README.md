# Bosca

Bosca is a personal file management system implemented in micro-service architecture.

## To run the project
The easiest way to run the project is to open it in IntelliJ IDEA. The script to run each service has been configed within IDEA project.

### Following steps should be taken to run the project smoothly:

1. Run docker of Zipkin locally, and map the ports to let local services user it:

   ```zsh
   docker run -d -p 9411:9411 openzipkin/zipkin
   ```

2. Run docker of RabbitMQ locally, map the ports to local services and configure admin user:

   ```shell
   # run RabbitMQ
   docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:3-management
   
   # config admin user
   docker exec -i -t <docker_container_id> bin/bash
   rabbitmqctl add_user bosca h4pHR*aAYhD6_z99oGkv
   rabbitmqctl set_permissions -p / bosca ".*" ".*" ".*"
   rabbitmqctl set_user_tags bosca adminstrator
   rabbitmqctl list_users
   ```

3. Run Config Server in IDEA, and wait the process to finish.
4. Run Service Discovery and API Gateway in IDEA.
5. Run User-WS, File-WS and Metadata-WS in IDEA.