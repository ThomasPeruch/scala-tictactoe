import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn
import scala.util.Random

var board = ArrayBuffer[ArrayBuffer[String]]()

@main
def ticTacToe(): Unit = {
  for (i <- 0 to 2)
    val row = ArrayBuffer[String]()
    for (j <- 0 to 2)
      row.addOne("")
    board.addOne(row)
  printBoard()

  var continueGame = true

  while (continueGame) {
    println("Enter a position (e.g. 1,1)")
    val input = StdIn.readLine()
    try {
      val positions = input.split(",")
      var x = positions(0).trim.toInt
      var y = positions(1).trim.toInt

      var skipRound = false;

      if(board(x-1)(y-1) !="")
        println("That positions is already taken, try again")
        skipRound = true;
      else {
        board(x - 1)(y - 1) = "X"
        printBoard()
      }

      if(!skipRound) {
        val playerWon = checkWinner(true)
        if(playerWon){
          println("You won")
          continueGame = false
        } else {
          placeComputeMove()
          printBoard()
          val computerWon = checkWinner(false)
          if (computerWon){
            println("You lose, computer won")
            continueGame = false
          }
        }
      }
    }catch {
      case e: Exception => println("Invalid input, please try again")
    }
  }
}

def printBoard(): Unit = {
  println("-------------")
  for (i <- 0 to 2)
    for (j <- 0 to 2)
      board(i)(j) match {
        case "X" => print("| X ")
        case "O" => print("| O ")
        case _ => print("|   ")
      }
    println("|")
  println("-------------")
}

def randomPosition(): Int = Random().nextInt(3)

def placeComputeMove(): Unit = {
  var i = randomPosition();
  var j = randomPosition();
  while (board(i)(j) != "")
    i = randomPosition();
    j = randomPosition();
  board(i)(j) = "O"
}

def checkWinner(player: Boolean): Boolean = {
  var won = false
  var checkSymbol = if(player) "X" else "O"
  for(i <- 0 to 2)
    //horizontal win
    if(board(i)(0) == checkSymbol && board(i)(1) == checkSymbol && board(i)(2) == checkSymbol){
      won = true
    }
    //vertical win
    if(board(0)(i) == checkSymbol && board(1)(i) == checkSymbol && board(2)(i) == checkSymbol){
      won = true
    }

    //diagonal win
    if (board(0)(0) == checkSymbol && board(1)(1) == checkSymbol && board(2)(2) == checkSymbol) {
      won = true
    }

    if (board(2)(0) == checkSymbol && board(1)(1) == checkSymbol && board(0)(2) == checkSymbol) {
      won = true
    }
  return won
}