# API
## Add Endpoints
- For all the available types of AI to play against.
- Add if the there is a winner to the current board.


-----------------------------------------------------------------------------------------
# AI 
- [x] Minimax 
- [ ] Find next version too add. (doesn't need to be in java.)
## Enhancements:
- Find any performance improvements.
    - Without adding multithreadings obviously.
- Add a test suite (good learning experience)
- Add a logger     (another good learning experience)


-----------------------------------------------------------------------------------------
# Frontend - Starting complete rework...

# Enahncement:
    - Remove a lot of the useless modules (there is like million)
    - Introduce a CSS library, I suck with CSS sooooooooo.
    - Do we use a different framework...
    - We could consider use typescript as well?

# Start with:
    1. Ground up rewrite following better React conventions.
        a. Design what pages will exist.
        b. Design of each page.
        c. Build out some of the components
        d. integrate with custom hooks
        e. Add to library for checking for winners.

-----------------------------------------------------------------------------------------
# Clean
# Optimization
# Testing

-----------------------------------------------------------------------------------------
# Deployment

## Is there a repo I can use for my build images?
- I can build my images, but is there a way to just get them on my server?
- Do I have to jsut `scp` them from my device to server and put in podman folder
    - DockerHub baby.

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

