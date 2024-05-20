# PTBOX Assignment - server

## How to run
1. Clone this repository, including the `theHarvester` submodule (you can use `--recurse-submodules`)
2. Run from the project root the dependencies script inside the `infra/` package. This makes sure that the 2 needed docker images are available on the host machine.
3. Create an `.env` file in the root package. `.env-example` contains a set of default values.
4. Run `docker compose up`

## How to use
Check the `docs/` package for the api documentation. OpenAPI is used to document the HTTP endpoints while AsyncAPI is used to document the WS.
The appplication runs by default on port 8080.
