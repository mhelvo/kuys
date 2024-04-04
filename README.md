# kuys
In this proof of concept (POC), I aim to explore simplifying the implementation of a clean architecture within Java.

We set the following objectives:

* Can the setup be designed to keep the domain clean, without infrastructure-related dependencies, such as controllers, databases, or external dependencies?
* Can this be achieved with minimal additional code? If additional code is necessary, can it be structured in a generic way so that a small change essentially does not require extra work?
* Can the setup allow running the domain in a Spring Boot app, a Micronaut app, and a Quarkus app, without utilizing any dependencies from these frameworks in the domain?
* Can the framework be structured to allow choosing a strategy for handling the wiring?

Within the domain, certain dependencies will be necessary. However, the goal remains to accomplish as much and as clean as possible with minimal lines of code. The following rules are allowed for the domain:
* Java API
* Custom annotations and a processor
