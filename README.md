# GetYourGuide test code

## Features

- Control over offline/online mode, using LiveData to react quickly to changes.
- List of reviews from server when there is connection.
- Reviews stored in a Room database.
- List of reviews from database if there is no connection.
- A small indicator will tell the user the status of the internet in case of being offline, then the indicator will change immediatelly after the connection is back to refresh the updated data.
- Everything controlled and handled with LiveData and ViewModel from the android architecture components. 
- Simulated add review, with validations (view section below).

## Left behind

Unfortunately I left behind a few things:
- Filters/sorting.
- Paging. In the current state only the first 25 reviews are displayed.
- Completed post payload (view section below).

## Would have been nice

I started, but got cut off because of the lack of time, implementing the Paging library by google. I have never used it before and I wanted to take advantage of this test to use it. I will probably implement it after getting the feedback.

## add review payload

The payload suggested would be basically the same as we get with few exceptions:
- the id will be generated in the server side
- language code I guess will be identified in the server as well. I wouldn't let the user to choose it, as well as I dont think is good idea to rely on the user's locale. 
- author, as the combination of reviewer name and reviewer country also could be generated in the server.

The response, in my opinion, should have the exact same format than the GET operation. Wrapped in an object with metadata about the POST operation (status code, etc) 