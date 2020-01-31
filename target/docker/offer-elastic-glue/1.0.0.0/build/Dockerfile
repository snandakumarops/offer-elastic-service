FROM fabric8/s2i-java:2.3
ENV JAVA_APP_DIR=/deployments
LABEL org.label-schema.description="Payment Received Glue for Case Mgmt" org.label-schema.version=1.0.0.0 org.label-schema.schema-version=1.0 org.label-schema.build-date=2020-01-22T21:50:01.047 org.label-schema.name="Payment Received Glue for Case Mgmt" org.label-schema.vcs-ref=3d717e8b08a3495a04bcf9c89dbb59795f751b30 org.label-schema.vcs-url=https://github.com/snandakumar87/customer-repository.git
EXPOSE 8080 8778 9779
COPY maven /deployments/
