SixDegreesOfKevinBacon
======================

This repository contains my solution to an assignment given during my secoond year at Aberystwyth Univsersity in 2009. As part of an alogirmths and data structures module, I was asked to provide an efficient solution to the Six Degrees of Kevin Bacon problem.

Suggested Improvements
======================

- The original data source for movies and actors was a text file download from IMDB. This no longer exists, and it would be desirable to find a new source of this data which is up to date.
- Bacon numbers are currently worked out at run time. This is ineffcient and can lead to delays of several second when searching. Re-work this so that all bacon numbers are calculated during the initialisation phase.
- The user is required to enter the actors names in a precise format, this should be simplified to a first last format. Providing suggestion for actors with identical names would also be a useful change.
