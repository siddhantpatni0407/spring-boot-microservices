# spring-boot-kafka-service

Spring Boot App with Kafka Service.

- This contains 2 modules
    1. spring-boot-kafka-producer
    2. spring-boot-kafka-consumer

## Kafka - Sequence Diagram

```mermaid
sequenceDiagram
  participant Producer
  participant Broker
  participant Topic
  participant Partition
  participant Consumer
  
  Producer->>Broker: Connect to Broker
  Broker->>Producer: Acknowledge Connection
  
  loop Produce Messages
      Producer->>Topic: Send Message
      Topic->>Partition: Store Message
  end
  
  Broker->>Consumer: Connect to Consumer
  Consumer->>Broker: Subscribe to Topic
  
  loop Consume Messages
      Broker->>Partition: Fetch Message
      Partition->>Consumer: Send Message
      Consumer->>Partition: Acknowledge Message
  end   
```

## Kafka - Flow Diagram - 1

```mermaid
flowchart TD
  subgraph Kafka Cluster
    Zookeeper -- Manages --> KafkaBrokers
    KafkaBrokers -- Stores --> Topics
    KafkaBrokers -- Replicates --> KafkaBrokers2
  end
  KafkaProducers -->|Produce| KafkaBrokers
  KafkaBrokers -->|Consume| KafkaConsumers
  
```

## Kafka - Flow Diagram - 2

```mermaid
flowchart LR
  subgraph Cluster1[Cluster 1]
    BrokerA1[Broker 1]
    BrokerA2[Broker 2]
    BrokerA3[Broker 3]
  end

  subgraph Cluster2[Cluster 2]
    BrokerB1[Broker 1]
    BrokerB2[Broker 2]
    BrokerB3[Broker 3]
  end

  Producers[Producers] -->|Publish messages| Topics
  Consumers[Consumers] -->|Subscribe to Topics| ConsumerGroups[Consumer Groups]

  Topics -->|Stored in| BrokerA1 & BrokerA2 & BrokerA3
  Topics -->|Stored in| BrokerB1 & BrokerB2 & BrokerB3

  BrokerA1 & BrokerA2 & BrokerA3 .-> |Replicate data| Sync[Sync replication]
  BrokerB1 & BrokerB2 & BrokerB3 .-> |Replicate data| Sync

  Zookeeper[Zookeeper] -->|Manage Brokers| Cluster1 & Cluster2
  SchemaRegistry[Schema Registry] -->|Manage Schema| Topics

  Connect[Kafka Connect] -->|Import/Export Data| ExternalSystems[External Systems]

  StreamProcessing[Kafka Streams] -->|Process & Transform| Topics
```

## Kafka - Flow Diagram - 3

```mermaid
flowchart TB
  subgraph Client
    Producers[Producers] --> |publish| KafkaCluster[Kafka Cluster]
    Consumers[Consumers] --> |subscribe| KafkaCluster[Kafka Cluster]
  end

  subgraph KafkaCluster[Kafka Cluster]
    Broker1[Broker 1] -.-> |replicate| Broker2[Broker 2]
    Broker2[Broker 2] -.-> |replicate| Broker3[Broker 3]
    Broker3[Broker 3] -.-> |replicate| Broker1[Broker 1]

    Broker1 --> |fetch| ConsumerGroup1[Consumer Group 1]
    Broker2 --> |fetch| ConsumerGroup2[Consumer Group 2]
    Broker3 --> |fetch| ConsumerGroup1

    Zookeeper[Zookeeper] --> |manage| Broker1
    Zookeeper --> |manage| Broker2
    Zookeeper --> |manage| Broker3
  end

  subgraph Topics
    Topic1[Topic 1] --> Partition1[Partition 1]
    Topic1 --> Partition2[Partition 2]
    Partition1 -.-> Broker1
    Partition2 -.-> Broker2
  end

  KafkaCluster --> Topics
```

## Kafka - Flow Diagram - 4

```mermaid
flowchart TD
  subgraph Cluster [Kafka Cluster]
    direction TB
    subgraph BrokerA [Broker 1]
      TopicA1[Topic A Partition 1]
      TopicA2[Topic A Partition 2]
      TopicB1[Topic B Partition 1]
    end
    subgraph BrokerB [Broker 2]
      TopicA3[Topic A Partition 3]
      TopicB2[Topic B Partition 2]
      TopicC1[Topic C Partition 1]
    end
    subgraph BrokerC [Broker 3]
      TopicB3[Topic B Partition 3]
      TopicC2[Topic C Partition 2]
      TopicC3[Topic C Partition 3]
    end
  end

  ProducerA[Producer A] -->|Publish| TopicA1
  ProducerA -->|Publish| TopicA3
  ProducerB[Producer B] -->|Publish| TopicB1
  ProducerB -->|Publish| TopicB3

  ConsumerGroupA[Consumer Group A] -->|Subscribe| TopicA1
  ConsumerGroupA -->|Subscribe| TopicA3
  ConsumerGroupB[Consumer Group B] -->|Subscribe| TopicB1
  ConsumerGroupB -->|Subscribe| TopicB2
  ConsumerGroupB -->|Subscribe| TopicB3

  Zookeeper[Zookeeper] -->|Manages| BrokerA
  Zookeeper -->|Manages| BrokerB
  Zookeeper -->|Manages| BrokerC
```
# Apache Kafka Commands

This README provides a guide on how to use Apache Kafka with various commands.

## Prerequisites

Before running Kafka commands, make sure you have Kafka installed on your system. If not, download and install it from the [official website](https://kafka.apache.org/downloads).

## Steps

1. **Go to Kafka Installation Directory**: Navigate to the Kafka server installation directory on your system. For example:
    ```bash
    cd C:\Softwares\Kafka\kafka\bin\windows
    ```

2. **Start Zookeeper Server**: Open a command prompt and run the following command to start the Zookeeper server:
    ```bash
    ./zookeeper-server-start.bat ../../config/zookeeper.properties
    ```

3. **Start Kafka Server**: Open another command prompt and run the following command to start the Kafka server:
    ```bash
    ./kafka-server-start.bat ../../config/server.properties
    ```

4. **Create a Topic**: Use the following command to create a topic named `my-topic`:
    ```bash
    ./kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic my-topic
    ```

5. **List All Topics**: Run the following command to list all topics available in your Kafka cluster:
    ```bash
    ./kafka-topics.bat --list --bootstrap-server localhost:9092
    ```

6. **Delete a Topic**: To delete a topic, replace `<topic_name>` with the name of the topic you want to delete and run the following command:
    ```bash
    ./kafka-topics.bat --delete --topic <topic_name> --bootstrap-server localhost:9092
    ```

7. **Start a Producer**: Use the following command to start a Kafka producer for the `my-topic` topic:
    ```bash
    ./kafka-console-producer.bat --broker-list localhost:9092 --topic my-topic
    ```

8. **Start a Consumer**: Run the following command to start a Kafka consumer for the `my-topic` topic, consuming messages from the beginning:
    ```bash
    ./kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my-topic --from-beginning
    ```

9. **Stop Kafka Server**: To stop the Kafka server, run the following command:
    ```bash
    ./kafka-server-stop.bat
    ```

10. **Stop Zookeeper Server**: Finally, stop the Zookeeper server by running the following command:
    ```bash
    ./zookeeper-server-stop.bat
    ```
