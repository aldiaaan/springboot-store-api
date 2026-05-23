.PHONY: services services-down clean

services:
	docker compose -f docker-compose.local.yml up -d

services-down:
	docker compose -f docker-compose.local.yml down

clean:
	docker compose -f docker-compose.local.yml down
	rm -rf .tmp

up:
	docker compose up -d

down:
	docker compose down