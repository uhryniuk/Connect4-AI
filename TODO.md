# API
- Delete all crumming endpoints.

## Create Test endpoints.
- Write simple endpoint to hit to make sure the server is alive.
    - This endpoint can live on the "/" path from localhost.
    - Since we are not using this as a webserver anymore.


-----------------------------------------------------------------------------------------
# AI

## Horizontal weirdness with AI 
- Horizontal and Vertical heuritics seem to be broken.
- horizontal is never valued it seems.

    ### We need to add priority values to each of the methods of scanning
    - Diagonal and Vertical may be more or less equal
        - I think diagonal is more valuable, since it is so much harder to see.
    - But obviously here, horizontal needs to be valued greater than vertical
        - This is where the issues stem
        - So the potential **Ranking Order** is as follows:
            1. Horizonal
            2. Diagonal
            3. Vertical
            - OR
            1. Diagonal
            2. Horizonal
            3. Vertical
            - The answer to which is better simple needs testing I think.

    - Currently we are using a better summing method to get a final value for the weiht of the board.
        - This still needs more research on which is better to use though.
        - So refer to the **active changes being made in the BoardEvaluator Class**


## Doesn't see "bad" moves
- When there is 2 in a row and it's the highest value
    - but there is only 1 more spot above that position.
    - The AI will pick it, even though it can't make 4 in a row..
        - For obbvious reasons this is stupid.

## More Dynamic first pics
- Perhaps assign weights to each row level
    - Consider if no good move can be made, select a new "opener"
        - This would be selecting a nother column without any coins.

-----------------------------------------------------------------------------------------
# Frontend

## Player losing, when there is no winner yet.
- Sometimes when there is no winner, it will simply say that the player loses...
- This usually happens when the AI gets 3 in a row.
    - But Once looking closer, there is no 4 in a row to be found.
    - Can't recall which direction it was 3 in a row though.


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

