# Codeclan Project 2:  Android Fitness tracker app

In the second project I decided to produce an Android app to track fitness activities.  What I had in mind was the manual entry that a user can do on an app such as Strava.  This is useful for enterring activities for which there is no associated gps file.

This app makes use of the new Room persistence framework for Android.  This makes it easier to work with SQLite databases by abstracting away some of the complexity.  This was a very interesting project as I had never used Android before and was keen to see what it was like.  I'm continuing to develop this project and plan to put in LiveData as well as refatoring to use a Model-View-Presenter pattern.  The other intended improvement is to introduce multi-threading so that the db is not having to work on the main thread used by the UI.
