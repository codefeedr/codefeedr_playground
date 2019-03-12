# Duplication

## Useful commands

Get amount of records in Kafka topic.
```bash 
./kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic TOPIC_NAME --time -1 | while IFS=: read topic_name partition_id number; do echo "$number"; done | paste -sd+ - | bc
```