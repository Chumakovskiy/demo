###Task description

**Problem definition**

Create a tiny RESTful web service with the following business requirements:
- Application must expose REST API endpoints for the following functionality:
    - ask question
    - list all accepted questions
    - list all accepted questions by country code

- User should be able to ask question publicly by providing question text.
- Service must perform question validation according to the following rules and reject question if:
    - Question contains blacklisted words listed in a dictionary
    - N questions / second are asked from a single country (essentially we want to limit number of questions coming from a country in a given timeframe)

- Service must perform origin country resolution using the following web service and store country code together with the question. 
Because networking is unreliable and services tend to fail, let's agree on default country code - "lv".

**Technical requirements**

You have total control over framework and tools, as long as application is written in Java. Feel free to write tests in any JVM language.

**What gets evaluated**
- Conformance to business requirements
- Code quality, including testability
- How easy it is to run and deploy the service (don't make us install Oracle database please ;)
Good luck and have fun!