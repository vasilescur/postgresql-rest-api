PostgreSQL HTTP Rest API [![Build Status][travis-ci_status_img]][travis-ci_status] [![Coverage Status][coveralls_status_img]][coveralls_status]
======

This is an HTTP Rest API for PostgreSQL databases.

Benefit of an HTTP API is to allow easy access to existing data. This means accessing data without bulding a web API (e.g. accessing data from Mobile app, rapid prototyping, for public data), furthermore allows you to access to data with tools that don't support the PostgreSQL protocol such as curl, web browsers.



## Securit of the Database
Current implementation does not have any security layer, meaning you will be exposing your database.

## Installation
You start the application with a configuraiton file, which contains your database credentials. Only other requirement is JDK 6 or later. 

1. Download the application(pg-rest-api-0.1.0.tgz) from [relese-page] and uncompress it
2. Prepare your configuration file (e.g. your database settings)
3. Set the port and config file path and run it 


```
/pg-rest-api-0.1.0/bin/pg-rest-api  -Dhttp.port=8081 -Dconfig.file=/path/to/config.conf 
```

 
Please refer to last section for configruation file.


## How to fetch data

###List of Tables
You can fetch list of tables like this:

	- http://localhost:9000/tables
	
which will return 

```
[
    {
        "name": "authors",
        "data": "http://localhost:9000/table/authors",
        "columns": "http://localhost:9000/table/authors/columns"
    },
    {
        "name": "books",
        "data": "http://localhost:9000/table/books",
        "columns": "http://localhost:9000/table/books/columns"
    },
    {
        "name": "customers",
        "data": "http://localhost:9000/table/customers",
        "columns": "http://localhost:9000/table/customers/columns"
    },
    ...
]
```


###Table Content
To get table content

	- http://localhost:9000/table/books
	
which will return 

```
[
    {
        "id": 7808,
        "title": "The Shining",
        "author_id": 4156,
        "subject_id": 9
    },
    {
        "id": 4513,
        "title": "Dune",
        "author_id": 1866,
        "subject_id": 15
    },
    ...
]
```


###Filter Table Content
You can filter the content by giving column name, following url will return the books that has subject_id 4

	- http://localhost:9000/table/books/subject_id/4
	
which will return 

```
[
    {
        "id": 41472,
        "title": "Practical PostgreSQL",
        "author_id": 1212,
        "subject_id": 4
    },
    {
        "id": 41473,
        "title": "Programming Python",
        "author_id": 7805,
        "subject_id": 4
    },
    ....
]
```


###Search in Table Content
You can also make text search, this will use PostgreSQL full text search support. Following query will search keyword python in title in books table 

	- http://localhost:9000/table/books/search/title/python
	
which will return 

```
[
    {
        "id": 41473,
        "title": "Programming Python",
        "author_id": 7805,
        "subject_id": 4
    },
    {
        "id": 41477,
        "title": "Learning Python",
        "author_id": 7805,
        "subject_id": 4
    }
]
```

You can also provide language parameter which will be used to normalize text and ignore stop words for search. For instance;

	- http://localhost:9000/table/books/search/title/the?language=english



###Get Columns
To get columns that belongs a table

	- http://localhost:9000/table/books/columns
	
which will return 

```
[
    {
        "column_name": "id",
        "data_type": "integer"
    },
    {
        "column_name": "title",
        "data_type": "text"
    },
    {
        "column_name": "author_id",
        "data_type": "integer"
    },
    {
        "column_name": "subject_id",
        "data_type": "integer"
    }
]
```



You can also provide language parameter which will be used to normalize text and ignore stop words for search. For instance;

	- http://localhost:9000/table/books/search/title/the?language=english


###Execute a Query
You can also post a complete SQL query which you want it to be executed, queries can be posted in following format


```
{
    "query": "SELECT * FROM books WHERE id=7808"
}
```

and send it to the:


	- http://localhost:9000/query

which will return 

```
[
    {
        "id": 7808,
        "title": "The Shining",
        "author_id": 4156,
        "subject_id": 9
    }
]
```



## Configuration File

This is an example configuration file that you can use it.


```
application.secret="IN_PRODUCTION_CHANGE_THIS_TO_A_LONG_RANDOM_STRING"

application.baseUrl="http://localhost:9000"
evolutionplugin=disabled
application.mode=prod

db = {
  default {
    driver="org.postgresql.Driver"
    url="jdbc:postgresql://localhost:5432/booktown"
    user="postgres"
    password="postgres"
  }
}

logger.application = INFO

```


## TODO
-Pagination 
-Add default functions (e.g. count)
-Show only requested columns not all
-Authentication


[travis-ci_status_img]: https://travis-ci.org/enginyoyen/postgresql-rest-api.svg?branch=master
[travis-ci_status]: https://travis-ci.org/enginyoyen/postgresql-rest-api
[coveralls_status_img]: https://img.shields.io/coveralls/enginyoyen/postgresql-rest-api.svg
[coveralls_status]: https://coveralls.io/r/enginyoyen/postgresql-rest-api?branch=master

[relese-page]: https://github.com/enginyoyen/postgresql-rest-api/releases/tag/v0.1.0
