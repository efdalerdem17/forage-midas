# forage-midas
# Transaction Processing System - JPMC Tech Job Simulation

A Spring Boot application developed during JP Morgan Chase & Co's job simulation that handles financial transactions using Kafka messaging, REST APIs, and real-time incentive calculations.

## Key Features

- Real-time transaction processing with Kafka integration
- Balance tracking and retrieval via REST API
- Incentive calculation system
- Transaction history management
- Concurrent transaction handling with deadlock prevention

## Technologies

- Spring Boot
- Apache Kafka
- JPA/Hibernate
- PostgreSQL
- SLF4J Logging
- RestTemplate

## Project Structure

- `com.jpmc.midascore.component`: Core transaction processing
- `com.jpmc.midascore.controller`: REST endpoints
- `com.jpmc.midascore.entity`: Data models
- `com.jpmc.midascore.repository`: Database access
- `com.jpmc.midascore.service`: Business logic
- `com.jpmc.midascore.incentive`: Incentive calculation

## Skills Demonstrated

- Event-driven architecture
- Distributed systems design
- Database transaction management
- RESTful API development
- Message queue integration
- Error handling and logging
- Test-driven development
