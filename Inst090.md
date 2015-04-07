#Installation and Usage Instructions - Beta 0.9.0

# Installation and Usage Instructions - Beta 0.9.0 #

## Installation ##

To "install", simply download the latest release from the [download section](http://code.google.com/p/adasim/downloads/list).

## Usage ##

### Prerequisites ###

Adasim expects to run on a JRE that has a Schema capable XML parser configured for its JAXP interface. Sun/Oracle JDKs from JDK 6 onwards come with Apache Xerces and thus fulfill this requirement.

The release JARs package all other required libraries, so no CLASSPATH setup is needed.

You also need a configuration file describing the simulation you want to run. Either create this file manually (the format is defined [here](XML_config.md)), or use the random configuration [Generator](Generator.md).

### Running Adasim ###

To run Adasim either call it directly from the commandline via

```
java -jar adasim-xxx.jar -I config.xml
```

(Replace xxx with the release number you downloaded and config.xml with the name of the configuration file you want to process.)

### Running the Configuration Generator ###

To run the configuration generator, call

```
java -cp adasim-xxx.jar traffic.generator.Generator <options>
```

The full commandline options for the generator are documented [here](Generator.md).

### Running multiple random simulations at one ###

To run multiple randomly generated simulations at once, you can use the scripts provided in the directory resources/scripts in the source download.

Their usage is documented [here](Scripts.md).