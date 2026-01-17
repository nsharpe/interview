# CORE

## Purpose
Stores common code that is cross concern.

### Demonstrates

One of the things this module originally held was the information about how to retrieve user information.  It was considered `core` functionality, until it became clear that there were cases where having user info isn't strictly required for some workflows.  This allows for common usage until `user` is moved into it's own submodule correctly.