# Connect4-AI
Simple Connect 4 WebApp that communicates to a server which uses a minimax algorithm to achieve optimal move responses.

## Explanation
The WebApp is the user interface in which the player actually plays against the AI with. 

The WebApp makes HTTP requests containing the current board state which is parsed by the server and in turn inits the minimax on the given board.

The WebApp itself contains the logic for deciding who the winner of the game is and tracks the "state" of the game. While the server and minimax was designed to be stateless, it's only concern is evaluting and returning a boards.

## Stack Used
---
The frontend WebApp was built using React and Vite, along with using `react-color` to make the color pickers.

While the backend is entirely written in Java with Maven as the build env. It only uses a single external lib which is `GSON`.

The decision to make a WebApp stems for the simplicity of sharing with others, with relative ease and speed of development.

Java was an easy choice, I am familiar with the build enviornment and I was more concerned about building something that worked rather than learning new tech or optimal performance.

I'd be quite curious of the performance differences when using different backends such as a "faster" systems language.

## Todo
---
- [ ] Fix matrix transposition when player wins.
- [ ] nginx reverse proxy config
- [ ] Adjust and test container files
- [ ] Put prebuilt images in Container Registry.

## Future Plans
---
Besides the squashing of current bugs, the future plans include:
- [ ] Adding other models of AI.
- [ ] Adding multiplayer capability to play against other users
- [ ] Higher quality mobile support
- [ ] Game statistics and leaderboards

## How to Run
---
Assuming some users may not want to install all of the prerequites, a simple way to run the application locally is with container manager such as Docker!

### Building WITH Docker
---
Prerequistes:
* Docker (or other container manager).

#### WebApp
---
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
---
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
---
To shutdown all of the container, run the following command:
```
docker stop connect4-frontend connect4-backend
```

### Building WITHOUT Docker
---
Prerequistes:
- NPM
- Node
- Java 8 or Higher

#### WebApp
---
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
---
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

#### Shutting Down
---
To tear down this environment, simple just use CTRL+C in all of the terminals that are running your frontend and backend processes.


## Disclaimer
I haven't done exhaustive testing in different enviornments so your results may vary wildly.
