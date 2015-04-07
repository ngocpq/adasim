# Rules #

These are rules, not guidelines. Everyone who writes/commits code should adhere to these.

  * Always remove unused imports
  * Always have a class header comment containing the license
  * Always have a Javadoc comment describing what a class/interface/enum does.
  * Always have a Javadoc comment describing what public/protected/default methods do
  * Always have comments that describe what methods do when they contain more than 5 lines of code or 10 statements.
  * **Never** use deprecated interfaces/classes/methods in new code.
  * **Never** commit code that does not compile.

# Guidelines #

These are additional guidelines that you should follow, but that are less strict than the mandatory rules.

  * Always aim for good design. Once your code works, refactor it into proper units and adapt your tests accordingly.
    * Deprecate interfaces/classes/methods when you no longer think they should be used.
    * Remove references to deprecated code when you refactor.
    * Focus on cohesion and coupling when you refactor.
    * Use good descriptive names when you refactor.
  * Use Eclipses/your compilers warnings/error settings to get warnings and errors when you have style violations in your code.
  * Use Eclipses pretty print feature to format/indent your code.