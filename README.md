![clarifind](https://raw.githubusercontent.com/mv740/clarifind/master/ic_clarifind.png)

##Scan an object and quickly find local businesses selling them

Android application built at [conuhacks 2017](https://devpost.com/software/clarifind-mgrj2u)

###Inspiration
We wanted to test the capabilities of the clarifai image processing API and its ability to generate search keywords for the yellow pages business search api.

###What it does
Users can scan an image of an object and get a list of possible keywords representing the object. Once the best keyword is selected a search based on the current location and keyword is performed using the yellow pages api in order to find local businesses that can provide what your looking for. A google maps view shows all nearby businesses which matched the yellow pages search performed.

###How we built it
The application was build for Android using android studio. In order to connect to the yellow pages api we used Retrofit 2.0 android library. Google maps API was also integrated into the project. We connected to clarifais API using there native client.

###Challenges we ran into
Chaining all the async tasks and trying to debug code when we were tired.

###Accomplishments that we're proud of
We managed to finish the application with all the functionalities we wanted in less than a day.

###What we learned
We gained experience using clarifai api and yellow pages api as well as native android functions for location, camera, maps.
