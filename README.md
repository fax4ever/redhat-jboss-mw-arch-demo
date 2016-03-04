# redhat-jboss-mw-arch-demo


## CALLS TO SERVICES:
# version
http://localhost:18080/sender/rest/version
http://localhost:18080/consumer/rest/version
http://localhost:18080/history/rest/version
http://localhost:18080/reader/rest/version

# services
http://localhost:18080/sender/rest/send
http://localhost:18080/reader/rest/counters
#NOT EXPOSED http://localhost:18080/history/rest/history?minutes=1
http://localhost:18080/reader/rest/query?minutes=1


