# Cinematic
This is an Improved version of the Popular Movies App, that's required to do in the Udacity 
Associate Android Developer course.

**Screens:**

<img src="screens.gif" alt="Screens" style="width: 80%;"/>

## Implementing The App:
This App uses :
- [Product Flavours](https://developer.android.com/studio/build/build-variants.html) to replace modules at compile time , providing fake data and stubs for automated testing.
- **MVP Architecture** The stubs based on [MVP](https://github.com/googlesamples/android-architecture)

**Features:**
- Discover Movies by high rating or most popular.
- Watch Trailers.
- Read Movie reviews.
- Mark a Movie as Favourite.
- Offline Work.
- Material Design Concepts.

**Requirements:**
- Java 8
- Latest version of Android SDK and Gradle build Tools

**API Key:**

The App uses [The movie Db](https://www.themoviedb.org) as Data source.
After obtaining the API key from the website, put the key in : 
`~/.gradle.properties`

```
Key="YOUR_API_KEY_HERE"
```
**Used Libraries:**
- [Picasso](http://square.github.io/picasso/) - A powerful Library that handles Image Loading.
- [ButterKnife](http://jakewharton.github.io/butterknife/) - Field and method binding for Android views.
- [Retrofit](http://square.github.io/retrofit/) - Type-safe HTTP client for Android and Java.
- [gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back.
- [mockito](http://site.mockito.org/) - Most popular Mocking framework for unit tests written in Java.
- [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) -  UI Testing framework.

### TO-Do
This App is My Playground and still in progress.

- Add Screenshots To Readme
- Implement Search for movies.
- Implement Content Providers for storing and retrieving Data.
- Recycler View Custom Animations.
- RxJava.

[![Analytics](https://ga-beacon.appspot.com/UA-100695310-1/chromuim/cinematic/readme?pixel)](https://github.com/igrigorik/ga-beacon)
