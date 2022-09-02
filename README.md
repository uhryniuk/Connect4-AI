# Connect4-AI
Simple Connect 4 WebApp written in React that communicates the board moves to a Java Server and AI which uses Minimax to calculate the optimal move for the AI to make.

## Explanation
The WebApp is the user interface which the player actually plays against the AI with. 

This WebApp the makes HTTP requests containing the current board state. This board state is then parsed by the AI Server and then calculates using Minimax the optimal board to return.

The WebApp itself contains the logic for deciding who the winner of the game is and tracks the "state" of the game. While the AI Server was designed to be stateless, it's game state agnostic and just analyzes a given board and returns an ideal move based on a hueristic function.

## Stack Used
The frontend WebApp was built using React and Vite, with React being the JS framework and vite being the build and deployment framework.

While the backend was built with pure Java and using Maven as the build environment. Some external libraries were used too, most notably GSON for simplifying the JSON parsing.

These reasons for making this a WebApp is for the ease of sharing my project with other people. I chose React and Vite simply because I was roughly familiar with React and Vite was much nicer to use than create-react-app.

I built the backend in Java because I had an existing HTTP server skeleton that made it simple to get the server up and running. I'll definitly consider rewriting with another, more performant language, to see the speed comparison.

## Future Plans
Besides the squashing of current bugs, the future plans include:
- [ ] Adding other models of AI.
- [ ] Adding multiplayer capability to play against other users
- [ ] Higher quality mobile support
- [ ] Game statistics and leaderboards

## How to Run
Assuming some users may not want to install all of the prerequites, a simple way to run the application locally is with container manager such as Docker!

### Building **with** Docker
Prerequistes:
* Docker (or other container manager).

#### WebApp
Start by navigating to the `connect4-frontend` directory by running the following
```
cd connect4-frontend
```

Then, build your WebApp container with the following:
```
docker build -t connect4-frontend:latest -f ./Containerfile .
```

Once your image is built you are able to run it with:
```
docker run --rm --name connect4-frontend -d -p 3000:3000 connect4-frontend
```

Now visit http://localhost:3000 to see the WebApp!

#### AI Server
To start building the AI server image, navigate to the `connect4-backend` directory:
```
cd connect4-backend
```

Begin building the AI Server image with the following command:
```
docker build -t connect4-backend:latest -f ./Containerfile .
```

Once the image is built, the run the following to run the server:
```
docker run --rm --name connect4-backend -d -p 8050:8050 connect4-backend
```

Then, you should be able to get responses from your webapp via localhost!

#### Shutting Down
To shutdown all of the container, run the following command:
```
docker stop connect4-frontend connect4-backend
```

### Building **without** Docker
Prerequistes:
- NPM
- Node
- Java 8 or Higher

#### WebApp
Start by navigating to the `connect4-frontend` directory by running the following
```
cd connect4-frontend
```

Like most Node applications, make sure you install the dependencies with the following:
```
npm install
```

Once the dependencies are installed you can then start your server wit:
```
npm run dev
```

Now visit http://localhost:3000 to see the WebApp!

If you are curious to what stack the frontend was built with, it is React using Vite for the slimmer dev experience and raw CSS.

#### AI Server
To start building the AI server image, navigate to the `connect4-backend` directory:
```
cd connect4-backend
```

If you are on UNIX based os, you have it easy since you can easily run the following start script:
```
./runServer.sh
```

Alternatively, you can run the commands that are inside the script:
```
mvn clean compile assembly:single
java -jar "./target/connect4-backend-1.0-SNAPSHOT-jar-with-dependencies.jar" 
```

Once ran, this will start the server thus making the game operational (if you are already running the frontend that is).

####
To tear down this environment, simple just use CTRL+C in all of the terminals that are running your frontend and backend processes.
