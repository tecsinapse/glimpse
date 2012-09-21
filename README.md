#Glimpse

Glimpse is a client/server tool that allows one to execute Groovy scripts 
inside a Spring Application.

Currently there is one client implementation as an Eclipse plugin. 
There could be potentially other implementations such as other IDE's plugins, 
command line based, browser based, and so on.

We also envision porting Glimpse to run in a CDI environment.

## Use Cases

* Exploratory development: allows one to easily invoke code in an application, 
so they can have a taste of how the code works. That doesn't replace unit tests, 
but we know that in reality is not always easy to set the environment to execute 
code from a unit test. And some times you just want to experiment.
* Testing code unreachable from the user interface: things like scheduled tasks can be tested without
the hassle of changing cron expressions just for the sake of testing.
* Loading/Extracting _ad hoc_ data: need to get data from that unexpected file your customer sent you, 
no need to write code in the application, just write a Groovy script.
* Generate test data: by using a general purpose language (Groovy), 
one can write a script to generate random data to be used in tests.
* Your imagination: Glimpse lets you do basically anything you could do using a Groovy script.  

## Features

* Every Spring singleton bean becomes a script variable, so one can invoke methods on those beans.
* REPL mode: type an expression and get the result.
* Progress monitoring: in case of long running scripts, it integrates with the IDE progress bar.
* Overwritten println: println's in scripts run by Glimpse are redirected to Glimpse's output (there is
an open issue to allow redirection of the entire application output to Glimpse).
* Extensibility: one can plug things like an authenticator for security purposes or a new source
for script variables (this will be the way to allow Glimpse to be run on CDI, for example).

## Why Groovy

Glimpse was created to be used in Java applications, and we find that Groovy is the easiest dynamic language
for a Java developer to learn. They basically already know it as almost any Java code is Groovy code also.
If you don't know anything about Groovy, we advise you to take a look at its website: http://groovy.codehaus.org/.
Even if you just learn the most basic features of Groovy, it will help you to take the most out of Glimpse.

## How to use

Please, refer to the [wiki] (https://github.com/tecsinapse/glimpse/wiki) for instructions on how 
to use Glimpse.

## License

Copyright (C) 2012 Tecsinapse

Glimpse is released under version 2.0 of the [Apache License] (http://www.apache.org/licenses/LICENSE-2.0).