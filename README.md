# Base
Contains base modules used in Assimbly.

The base has two kind of modules:

1. Modules that contain a set of Maven dependencies.
These modules can be used as a base to create
integration or broker modules.
2. Modules that contain util classes. (Currently the utils modules)

See how to:
- [Base](#base)
  - [Build](#build)
  - [Deploy](#deploy)


## Build

The base can be built with Maven:

```
mvn clean install
```

It's also possible to build only one module at the time.
For this, the same Maven command can be executed, but
from the directory that contains the pom.xml for that module.

For example:

```
cd ./databaseDrivers
mvn clean install
```
## Deploy

The base can be deployed with Maven:

```
mvn clean deploy
```

It's also possible to deploy only one module at the time
or deploy all modules.

For example for a single module deploy:

```
cd ./activemqBase
mvn clean deploy
```

For deploying all modules:

MacOS
```
sh ./bin/mac/deploymodules.sh
```

Windows
```
sh ./bin/win/deploymodules.sh
```
