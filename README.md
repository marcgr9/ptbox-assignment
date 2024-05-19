# PTBOX Assignment - server

## How to run
1. Clone this repository, including the `theHarvester` submodule (you can use `--recurse-submodules`)
2. Run the dependencies script inside the `infra` package. This makes sure that the 2 docker images needed are available on this machine.
3. Create an `.env` file in the root package. `.env-example` contains a set of default values.
4. Run `docker-compose up`
