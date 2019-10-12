# Utiliza como base a imagem do maven com as dependências
FROM registry.gitlab.com/crearj/devops-crea/maven-restapi AS build

# Copia o projeto
COPY . .

# Faz o build
RUN mvn clean install -Dmaven.test.skip=true

# Utiliza o wildfly como servidor de aplicação, será apenas um wildfly, e as definiçoes de dev ou prod ficarão no ambiente
FROM registry.gitlab.com/crearj/devops-crea/wildfly:dev AS release

# Copia o resultado do build para o wildfly
COPY --from=build /root/.m2/repository/br/org/crea/restapi/rest-api/0.0.1-SNAPSHOT/rest-api-0.0.1-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments
