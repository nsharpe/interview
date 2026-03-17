# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Quick Start

**Requirements:** Node.js, npm, Java 21, Docker

**Build and run:**
```shell
npm install
npm start
```

## Architecture Overview

This is a React-based UI application that serves as the frontend for a media management system. It connects to multiple backend services through TypeScript SDKs generated from OpenAPI specifications.

The application architecture consists of:
- A React frontend with routing using react-router-dom
- Multiple API clients for different backend services:
  * Media Management (Series/Content)
  * Public REST endpoints
  * Admin endpoints
  * Media Player event tracking
  * QA/generator endpoints

## Key Components

### API Clients
The application uses generated TypeScript SDKs from OpenAPI specifications located in sibling repositories. These are linked via file paths in package.json:
- `media-player-media-management-client` - For content management
- `media-player-public-rest-client` - For public user-facing APIs
- `media-player-admin-client` - For admin operations
- `media-player-qa-client` - For QA/generator endpoints
- `media-player-media-player-client` - For media player event tracking

### Authentication
The application uses a simple token-based authentication system:
- Default token is "123" for local development
- Tokens are stored in localStorage
- Authorization header is set via axios defaults
- TokenManager component provides UI for setting tokens

### Routing
Uses React Router with the following routes:
- `/` - Token setup page
- `/qa-dashboard` - QA dashboard with user/movie generators
- `/admin/user` - User management table
- `/series` - Series content dashboard
- `/player/:seriesId` - Media player for a specific series

## Development Commands

### Build and Run
```shell
npm install              # Install dependencies
npm start                # Run development server
npm run build            # Create production build
npm test                 # Run tests
```

### Key Files to Understand
- `src/App.tsx` - Main application routing
- `src/components/tokens/TokenManager.tsx` - Authentication handling
- `src/api/*.ts` - API client configurations
- `src/pages/Qa/QaDashboard.tsx` - QA dashboard main page
- `src/pages/ContentDashboard/SeriesDashBoard.tsx` - Content dashboard

## Backend Service Endpoints (Default Local Ports)
- Media Management: http://localhost:9090
- Public REST: http://localhost:9080
- Admin: http://localhost:9110/admin
- Media Player: http://localhost:9100
- QA Generator: http://localhost:9120

## Important Notes

- The application assumes a running backend stack with Docker Compose
- Default admin token is "123" for local development
- All API clients are configured to use localhost addresses
- The UI uses file-based linking for TypeScript SDKs, so the sibling repositories must be built first