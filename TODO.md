# API
- Delete all crumming endpoints.

## Create Test endpoints.
- Write simple endpoint to hit to make sure the server is alive.
    - This endpoint can live on the "/" path from localhost.
    - Since we are not using this as a webserver anymore.


-----------------------------------------------------------------------------------------
# AI

## Status:
- Somehow I complete it. Granted I used resources but I learned a lot I think.
    - Especially with Arrays and iterating in interesting ways.

-----------------------------------------------------------------------------------------
# Frontend

## The Winner system is boinked.
- If the AI wins, the game does not halt.
- Sometimes AI wins when there is 3 slots on the board.


## If AI responds with 4 in a row, game does not halt player
- The game foes not throw a "loser" banner either.
- Doesn't end game if the AI returns a winner

-----------------------------------------------------------------------------------------
# Clean
# Optimization
# Testing

-----------------------------------------------------------------------------------------
# Deployment

## Is there a repo I can use for my build images?
- I can build my images, but is there a way to just get them on my server?
- Do I have to jsut `scp` them from my device to server and put in podman folder

-----------------------------------------------------------------------------------------
# Build Cycle
## Write a simple, All in one build script
- This should build both the frontend and the backend
- Run all the tests for both of them
- Build their podman images
- Run them in containers
- Test the containers
- Shutdown the containers
- Provide useful output such as time, and resources used for containers.

