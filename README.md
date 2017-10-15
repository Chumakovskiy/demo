## Task description

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

# API description

- Add new question

| **URL** | /api/1.0/rest/questions |
| --- | --- |
| **Method** | POST |
| **URL Params** | **–** |
| **Data Params** | {           &quot;text&quot; :                        [String],}  |
| **Success Response** | **Code:** 200 OK **Content:**        {           &quot;id&quot;                                 [Number],           &quot;text&quot;                         [String],           &quot;countryCode&quot;                [String],           &quot;createdTime&quot;                [Number],        } |
| **Error Response** | **Code:** 400 **Content:** ErrorInfo (Text with words from black list)  **Code:** 403 **Content:** ErrorInfo (Too many requests from your country) |
| **Notes** | |
1. Default country = lv
2. Error 400 in case of text contains blacklisted words listed in a dictionary
3. Error 403 in case of limitation number of questions coming from a country in a given timeframe



- Get all questions

| **URL** | /api/1.0/rest/questions |
| --- | --- |
| **Method** | GET |
| **URL Params** | **Optional:** countryCode                        [String],offset                                        [Number],        //Integerlimit                                        [Number],        //Integer |
| **Data Params** | – |
| **Success Response** | **Code:** 200 OK **Content:** [       {           &quot;id&quot;                                 [Number],           &quot;text&quot;                         [String],           &quot;countryCode&quot;                 [String],           &quot;createdTime&quot;                [Number],       },       {              …       },       …] |
| **Error Response** | **Code:** 4xx **Content:** ErrorInfo(…) |
| **Notes** | |
1. If countryCode not empty and valid, method returns only questions from this country.
2. If offset is empty, it is understood as 0.
3. If limit is empty, it is understood as 100.


- Get questions count 

| **URL** | /api/1.0/rest/questions/count |
| --- | --- |
| **Method** | GET |
| **URL Params** | **Optional:** countryCode                        [String], |
| **Data Params** | – |
| **Success Response** | **Code:** 200 OK **Content:** {Number}                //Integer |
| **Error Response** | **Code:** 4xx **Content:** ErrorInfo(…) |
| **Notes** | |
1. If countryCode is not empty and valid, method returns only number of questions from this country.

## Runtime 
NOTE: to run this application it is need to create DB TEST_TASK, login - root, password - root