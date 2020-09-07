# rfe600-cookielogger project

A small quarkus based reproducer for the RFE-600.

this is the backend pod. If no COOKIE is set with the name as defined in 
application.properties, it will set it and redirect to the defined url.

That could be the same pod but needs to point to the static index.html. If you
then click on the OK button on the form in the right column, it should redirect
per POST to the original pod.

And depending on the samesite attribute of the haproxy session variable the
original pod should be answering again.

## Setup
1. Define 2 domain names (you need different domains, otherwise the SameSite won't kick in).
1. Setup at least 3 pods with this quarkus application running. Two loadbalanced as sticky with this software. Endpoint is /hello.
1. Setup another pod for the static page (of course you could do magic within the routes to make the domains differ).
1. Call the rest-endpoint domain-1/hello - you should be redirected to domain-2/index.html
1. Press the OK-button to get back to domain-1/hello
1. YOu should get the cookie displayed. And you can check the log output for all cookies set.