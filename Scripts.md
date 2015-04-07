# Introduction #

We have a couple of scripts that simplify experimenting with adasim. All scripts are contained in 'resources/scripts'.

|**Script**|**Purpose**|
|:---------|:----------|
| create-random.sh | simple bash shell script to randomly create a simulation and run it. All parameters are hard-coded in the script. |
|run-sim.py | A more flexible, python based extension of create-random.sh. Requires Python 2.7.2 or higher. |
| log2csv.py | Takes the output of run-sim.py and produces a CSV file with relevant data from the logs. |
| merge-csv.py | Merges all CSV files found in the current directory into a single CSV file and writes it to `data.csv`. It will ensure uniqueness of car IDs across different simulation runs. The file created is intended for use with `plot_time_data(file)`.|
|plot.R| R script to process the CSV files and create neat barplots |


# Details #

## run-sim.py ##

Before you run this script, add the adasim binaries to your CLASSPATH. On Unix like systems you can do so by issuing the following command:

```
export CLASSPATH=$CLASSPATH:adasim-release.jar
```

run-sim.py takes one commandline argument (-f filename). The file configures some parameters of the simulations to create and run. The file is a simple line-based text file. Each line has the format
```
#nodes:#cars:#iterations
```
Each of the colon separated values must be convertible to an integer using Python's int() function. The values define how many nodes and cars should be generated, and how many random simulations should be run with these settings.

For example, 100:50:10 specifies, that the script should generate and run 10 random simulations, each containing 100 nodes and 50 cars.

For each iteration the script will create two output files: the XML simulation configuration and the log output from running the simulation.

The filename convention is

#nodes-#cars-iteration.{suffix}

where the suffix is either 'xml' or 'log'.

### Customizing random generation ###

If you want to modify the parameters to `traffic.generator.Generator` that are used by this script, edit lines 38-40 in the script. These lines encode the parameters passed to the generator.

## log2csv.py ##

log2csv.py takes a directory as argument. The directory must contain the xml and log files created by running run-sim.py.

The CSV file will contain one line for each car in each simulation run. The data available are:
|Nodes|Cars|ID|Hops|Time | Strategy |
|:----|:---|:-|:---|:----|:---------|
| # of nodes in this sim run | # cars in the sim run | some unique ID for the line | # nodes passed from start to finish | # sim cycles taken from start to finish | car strategy employed |

## plot.R ##

plot.R provides a few simple functions to process that data files and plot the graphs into PDFs.
To use it, follow these steps:

  1. Start R in the directory where the log2csv output is
  1. Source plot.R (by typing `source("plot.R")`
  1. Call `plot_all_data()` Magic happens.

Alternatively, there is `plot_data(prefix)` that takes a filename prefix and prints the plot of the data in the CSV file starting with that prefix.

For example, if you have `100-100.csv`, call `plot_data("100-100")` and you'll get `100-100.pdf`.

Additionally, there is `plot_time_data(file_prefix)` that will plot a bar chart showing the number of iteration cycles needed. Data is  separated by the number of cars (it assumes the number of nodes to be fixed).