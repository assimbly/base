# Scripts usages

Scripts in this bin directory can be executed
for Windows from _/bin/win_ or Mac/Linux from _/bin/mac_

---
## build

Builds the project or module.

### Usage:

To build the complete project:

```sh build.sh```

To build a specific module

```sh build.sh modulename```

---

## buildandtest

Builds the project or module and then run the unit tests.

### Usage:

To build and test the complete project:

```sh buildandtest.sh```

To build and test a specific module

```sh buildandtest.sh modulename```

---

## lazygit

Add all files to the staging area, commit the files and push it to origin (GitHub). Shortcut for:

```
git add -A
git commit -m "<commit message"
git push
```

### Usage:

```sh lazygit.sh "my message"```

---

## checkversions

Checks the Maven dependencies for the latest versions.

The patterns in the rules.xml are excluded. The rules.xml is
in the root directory of the project.

### Usage:

To print a report:

```sh checkversions.sh```

---

## updateversion

Update the version number of the Maven project and its submodules.

### Usage:

To print a report:

```sh updateversion.sh <versionnumber>```

example

```sh updateversion.sh 1.1.0```
