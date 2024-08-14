# Makefile

# Java compiler
JC = javac

# Java compiler flags
JFLAGS = -g

# Default target
default: Server Client

# Compile and run the server
server:
	$(JC) $(JFLAGS) Server.java
	java Server

# Compile and run the client
client:
	$(JC) $(JFLAGS) Client.java
	java Client

# Clean up compiled files
clean:
	$(RM) *.class
