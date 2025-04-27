# chess.kt

[![pipeline](https://github.com/krossovochkin/chess.kt/actions/workflows/pipeline.yml/badge.svg?branch=main)](https://github.com/krossovochkin/chess.kt/actions/workflows/pipeline.yml)

Kotlin multiplatform chess backend  
Also contains example of usage as desktop app  

![](/img/chess_initial.png)
![](/img/chess_checkmate.png)

# Usage

## Create a game

Main class that holds all the game state is `Game`.  
Create it with `Game#create` method providing optional FEN of the starting position.  
If no FEN is provided then initial position will be used.

```kotlin
val game = Game.create()
```

## Moves

To make a move one should call `Game#move` with instance of the `Move` class.  
For easier usage one can use extension `String#asMove` that parses usual PGN notation move and generates corresponding instance of `Move` class.  
Calling `Game#move` will return `true` if move was successful or `false` otherwise.  

```kotlin
val isSuccess = game.move("Nbxd2".asMove())
```

## Getting piece

To get piece on the particular square of the board one should call `Game#getPiece` providing square to check.  
For easier usage one can use extension `String#asSquare` that generates corresponding instance of `Square` from file-rank string.  
Calling `Game#getPiece` will return piece if it is on the given square or `null` otherwise.  

```kotlin
val piece = game.getPiece("e4".asSquare())
```

## Getting available moves

To get available moves call `Game#availableMoves` providing optional starting square.  
List of available moves will be returned. If there are no moves empty list will be returned.  

```kotlin
val availableMoves = game.availableMoves("d4".asSquare())
```

## Game end

To get notified on game end set up callback via `Game#setGameResultCallback`.  
When game ends callback will be triggered with type of ending.

```kotlin
game.setGameResultCallback { result ->
    if (result is GameResult.Draw) {
        // do sth
    }
}
```

# License
Copyright © Vasya Drobushkov  
License Apache 2.0
