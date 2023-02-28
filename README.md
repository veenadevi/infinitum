Infinitum is a set of Java libraries that aim to achieve the
following goals:

1. **Agility**: Allow teams to kick-off test automation projects quickly;
2. **Reuse**: Allow teams to leverage best practices collected over the course of several similar projects;
3. **Efficiency**: Implement features commonly encountered on automation projects without having to rewrite them every time they are needed;
4. **Quality**: Have assurance that common patterns utilized in test automation projects are of the highest quality and have been tested thoroughly to meet desired expectations.

Infinitum is made available as small, pre-compiled, individual
Maven modules. This allows teams to pick and choose modules that
are relevant to them, based upon the needs of the project they
are working on. In addition to this, modularity of the libraries
keeps the overall size of an automation project in check, while
allowing teams to add modules as and when required.

# Pre-requisites

---
* Install Java Development Kit (JDK) 8
* Create `JAVA_HOME` environment variable to point to the
  directory under which JDK is installed - e.g.
  `C:\Program Files\Oracle\Java Development Kit` on Windows
  or `/opt/oracle/jdk` on Linux or Unix-like systems
* Add `JAVA_HOME/bin` to the `PATH` environment variable -
  e.g. `PATH=%PATH%;%JAVA_HOME%\bin` on Windows or
  `PATH=$PATH:$JAVA_HOME/bin` on Linux or Unix-like systems
* Test Java installation by opening a command-prompt window,
  issuing `java -version` and making sure a valid output is
  printed
* Install Apache Maven 3.5
* Create `MAVEN_HOME` environment variable to point to the
  directory under which Maven is installed - e.g.
  `C:\Program Files\Apache\Maven` on Windows
  or `/opt/apache/maven` on Linux or Unix-like systems
* Add `MAVEN_HOME/bin` to the `PATH` environment variable -
  e.g. `PATH=%PATH%;%MAVEN_HOME%\bin` on Windows or
  `PATH=$PATH:$MAVEN_HOME/bin` on Linux or Unix-like systems
* Test Maven installation by opening a command-prompt window,
  issuing `mvn -v` and making sure a valid output is printed
* Install a Git client
* Clone this repository locally

# Building

---
The entire project can be built simply by running
`mvn clean package`.

# Developing

---
Review details on the
[Infinitum Development page](https://sites.google.com/qualitrix.com/infinitum/infinitum-development)
for details on how to develop Infinitum modules.

**Note**: Please make sure to review the page above to gain
familiarity with the Infinitum development workflow. Do not start
developing code for Infinitum without reviewing and understanding
the information provided on the aforementioned page. Not
following the workflow described on that page may require redoing
all work in certain circumstances.

# Documentation

---
If you are interested simply in using Infinitum for a test
automation project, please visit the
[Infinitum Website](https://sites.google.com/qualitrix.com/infinitum)
for details on Infinitum features and usage instructions.