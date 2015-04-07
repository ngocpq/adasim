# Useful things to know about working with Adasim source #

  * Take make Adasim run from source within Eclipse, you must explicitly add the main project directory to the classpath (this is not done by default!)
  * Program versioning is coded in traffic/Version.java and the actual version numbers are in resources/VERSION. These should only be changed when an official release is made.
  * Whenever a commit also closes an issue, prefix your commit message with "Fixes issue #" (and replace the '#' with the actual issue number). This will automatically close the issue and link back to the revision that closed it.