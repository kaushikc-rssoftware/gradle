FROM gradle:jdk17-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app
# ARG BUILD='WER'
#
# ENV BUILDvar=$BUILD

WORKDIR $APP_HOME/
COPY . .
RUN mkdir -p $APP_HOME/build/libs/

RUN gradle bootJar

# actual container
FROM gradle:jdk17-alpine
ENV ARTIFACT_NAME=BillEdgeDemo-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app
ARG BUILD='WER'
ENV BUILDvar=$BUILD
ARG DISTRIBUTION_IMAGE_DIR='w'
ENV DISTRIBUTION_IMAGE_DIR_VER=$DISTRIBUTION_IMAGE_DIR
RUN echo $BUILD

# ARG items
# RUN for item in $items; do \
#     echo "$item"; \
#     done;
WORKDIR $APP_HOME/

COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD java -jar $ARTIFACT_NAME