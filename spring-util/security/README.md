# Security Module

This module provides Spring Security configuration and authentication utilities for the application.

## Overview

The security module implements a flexible authentication system that supports:
- Conditional security based on configuration properties
- Role-based access control (RBAC)
- Token-based authentication with Redis integration
- CORS configuration

## Key Components

### SecurityConfiguration

The main security configuration class that sets up Spring Security filters and authentication mechanisms.

**Conditional Properties:**
- `spring.security.enabled`: Enables/disables security (default: true)
- `spring.security.role`: Specifies required role for access (default: ANY)

When security is enabled:
- Uses a custom `TokenAuthenticationFilter` to validate tokens
- Configures CORS with appropriate settings
- Sets session management to stateless

When security is disabled:
- Disables CSRF protection
- Allows all requests without authentication

## Configuration

### Required Properties

```properties
# Enable/disable security (default: true)
spring.security.enabled=true

# Required role for access for all endpoints of the application (default: ANY)
# Valid values: ANY, ADMIN, USER, etc.
spring.security.role=ANY
```
