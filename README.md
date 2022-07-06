# base
Contains base modules used in Assimbly.

The base has two kind of modules:

1. Modules that contain a set of Maven dependencies.
These modules can be used as a base to create
integration or broker modules.
2. Modules that contain util classes. (Currently the utils modules)


# build

The base can be build with Maven:

```mvn clean install```

It's also possible to build only one module at the time.
For this the same Maven command can be executed, but then
from the directory that contains pom.xml of that module.

For example:

```
cd ./databaseDrivers
mvn clean install
```


