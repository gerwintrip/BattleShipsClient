# BattleShipsClient
BattleShips is a simple game where there is a 2 dimensional grid of 25 by 25 tiles. Each player is given a ship that consists of 3 tiles, which is then placed onto this grid at a random location. Players can each send one torpedo a turn at chosen coordinates. The game will then tell the player if they hit an enemy ship, or if they missed their shot. A winner is declared when all but one ship have sunk.

This is the client for the BattleShips game. The server can be found at [BattleShips](https://github.com/AppleSaph/BattleShips).

## Installation
1. Download the latest [releases](https://github.com/gerwintrip/BattleShipsClient/releases).
2. Create a new Java project in IntellIJ.
3. Add both jar files as a module dependency in the project. To do this, press `Ctrl+Alt+Shift+S` to open the Project Structure menu. From here navigate to `Modules` and then `Dependencies`. Click on the `+` icon and choose to add `JARs or Directories...`. Locate the downloaded jar files and click on `OK`.
4. Create your own client class, for example `MyBattleShipClient`, and make sure it inherits the `BattleShipsClient` class.
5. Create a `Main` class and declare a new instance of your own client in its main method.
6. Build your own user interface, for example by using the command line, to listen to events from and to send commands to the server.

## Server Protocol
Communication between the client and the server uses the following protocol. Although you don't need to, because we have already made methods that use this protocol for you, you could send command strings to the server with the `BattleShipsClient#sendCommand(String message)` method.

#### Client 🡪 Server
* `MOVE~x~y` - to launch a torpedo, where x and y are the coordinates of the move
* `EXIT` - to exit the game
* `PING` - to check if the server is still alive
* `PONG` - response to a PING from the server

#### Server 🡪 Client
* `HELLO~playerNumber` - where playerNumber is the playerNumber of the player
* `HIT~x~y~playerNumber` - where x and y are the coordinates of the hit, and playerNumber is the playerNumber of the player that is hit
* `MISS~x~y` - where x and y are the coordinates of the miss
* `WINNER~playerNumber` - where playerNumber is the playerNumber of the winner
* `LOST~playerNumber` - where playerNumber is the playerNumber of the player who lost
* `ERROR~message` - where message is the error message
* `EXIT` - to exit the game
* `TURN~playerNumber` - where playerNumber is the playerNumber of the player whose turn it is
* `NEWGAME~x~y` - where x and y are the width and height of the new game
* `POS~x~y` - where x and y are the coordinates of a ship part
* `PING` - to check if a client is still alive
* `PONG` - response to a PING from the client
