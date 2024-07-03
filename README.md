
•  Programming Language: Kotlin.
•  Architecture: Designed the application with the MVVM architecture pattern.
•  Dependency Injection: Implemented Dagger2 to manage dependencies effectively.
•  Network Layer: Developed using the Retrofit library for handling API requests and responses.
•  Jetpack Components: Leveraged components such as LiveData, ViewModel to enhance architecture, ensure reactive data handling, and simplify navigation within the app.

Testing Stragegy
•  Unit Testing: Validate individual components using JUnit and Mockito for correctness.
•  Integration Testing: Ensure interactions between components work as expected with tools like Espresso.
•  UI Testing: Automate user interface tests to verify the app's behaviour using Espresso and UI Automator.
•  Performance and Security Testing: Assess app performance with Android Profiler and conduct security scans using OWASP ZAP to identify vulnerabilities.

Further Enhancement – 

Bookmark/UnBookmark functionality – Currently I have used Shared Preferences to store/retrieve titles of bookmarked articles. Later when the login functionality is implemented, either the bookmarked articles can be store in Roon DB or on server side.
UI – Jetpack Compose can be used instead of XML based layouts

