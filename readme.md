# Spring Boot Redis Cache Basics

Install wsl on windows to add ubuntu:
https://learn.microsoft.com/en-us/windows/wsl/install

Windows terminal:
https://learn.microsoft.com/en-us/windows/terminal/install

Intellij IDE:
https://www.jetbrains.com/idea/download

Postman:
https://www.postman.com/downloads/

Postgres Install Windows:
https://www.postgresql.org/download/

Install redis on windows:
https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/install-redis-on-windows/

Start Spring IO:
https://start.spring.io/

## Install redis on windows ubuntu commands:

```bash
curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg

echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list

sudo apt-get update
sudo apt-get install redis
```


start redis server
```bash
sudo service redis-server start
```

test redis server is running
```bash
redis-cli
ping
```

get all redis keys
```bash
redis-cli
KEYS *
```

flush all keys
```bash
redis-cli
FLUSHALL
```
