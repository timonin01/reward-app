В проекте 2 сервиса:
- reward-calculation-app - сервис тарификации вознаграждений сотрудникам за дополнительную работу. Каждый сотрудник может выполнять что-либо помимо основной работы - проводить лекции, выступать на конференциях и т.д.
  Действия оплачиваются согласно тарифам, с учетом заслуг сотрудника (личного бонусного коэффициента).
- Оплата проходит через внешний сервис reward-payment-app, вызываемый по REST.
- Также создан отдельный модуль для написания acceptance и load тестов - reward-acceptance-tests


Технологии применяемые в проекте:
* VCS, Git, GitHub
* Build Tool Gradle
* Spring Framework, Spring Boot
* Design patterns: SAGA, Transactional outbox.
* REST
* SQL, MySQL, H2, JDBC, ORM, JPA, Hibernate, Liquibase
* JUnit, Mockito
* Lombok
* Docker, Docker-compose
* JDK 21
* Micrometer
* OpenTelemetry (OTLP)
* Jaeger
* Redis
* Local cache (@Cacheable, caffeine)
