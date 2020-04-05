package helpers

import com.google.common.annotations.VisibleForTesting
import helpers.Move.validMoves
import models.{Board, Row, Square, TourChunkRequest, TourRequest}

object ObjectFactory {

  def getOptimisedBoard(tourChunkRequest: TourChunkRequest): Board = boardFactory(tourChunkRequest).build.getOptimisedBoard

  def boardFactory(tourChunkRequest: TourChunkRequest): BoardFactory = {
    BoardFactory(
      0 until tourChunkRequest.board.rows map { rowIndex =>
        RowFactory(
          rowIndex, 0 until tourChunkRequest.board.columns map { colIndex =>
            SquareFactory(rowIndex, colIndex)
          })
      })
  }

  case class BoardFactory(rowFactories: Seq[RowFactory]) {

    def columnCount: Int = rowFactories.head.squareFactories.size

    def getSquare(row: Int, column: Int): Option[SquareFactory] = rowFactories.find(_.index == row).flatMap(_.getSquare(column))

    def build: BoardFactory = {
      val rows: Seq[Row] = rowFactories.map(_.build(this))
      optimisedBoard = Board(rows)
      this
    }

    private var optimisedBoard: Board = _

    def getOptimisedBoard: Board = optimisedBoard

    @Override
    override def toString: String = s"{rows: ${rowFactories.mkString("[\n    ", ",\n    ", "]")}}"

  }

  case class RowFactory(index: Int, squareFactories: Seq[SquareFactory]) {

    def getSquare(column: Int): Option[SquareFactory] = squareFactories.find(_.columnIndex == column)

    def build(board: BoardFactory): Row = {
      val squares: Seq[Square] = squareFactories.map(_.build(board))
      Row(index, squares)
    }

    @Override
    override def toString: String = s"{index: $index: squares: ${squareFactories.mkString("[", ",", "]")}}"
  }

  case class SquareFactory(rowIndex: Int, columnIndex: Int) {

    def destination(boardFactory: BoardFactory, squareFactory: SquareFactory)(move: Move): Option[StepFactory] = {
      boardFactory.getSquare(squareFactory.rowIndex + move.y, squareFactory.columnIndex + move.x)
        .map(square => StepFactory(move, square)(boardFactory))
    }

    def possibleSteps(board: BoardFactory): List[StepFactory] = validMoves.flatMap(destination(board, this)).toList

    def build(board: BoardFactory): Square = {
      val boardIndex = rowIndex * board.columnCount + columnIndex
      Square(rowIndex, columnIndex, boardIndex, possibleSteps(board).map(_.build(this)))
    }

    @Override
    override def toString: String = s"{$rowIndex, $columnIndex}"
  }

  case class StepFactory(move: Move, squareFactory: SquareFactory)(boardFactory: BoardFactory) {

    val resolver: SquareResolver = SquareResolver(squareFactory.rowIndex, squareFactory.columnIndex, boardFactory)

    def build(squareFactory: SquareFactory): Step = Step(move, resolver)
  }

  case class SquareResolver(row: Int, column: Int, boardFactory: BoardFactory) {

    def resolve: Square = {

      boardFactory.getOptimisedBoard.getSquare(row, column).get
    }

    @Override
    override def toString: String = s"{$row, $column}"
  }

  @VisibleForTesting
  def getTestBoard(tourRequest: TourRequest): Board = boardFactory(tourRequest).build.getOptimisedBoard

  @VisibleForTesting
  def boardFactory(tourRequest: TourRequest): BoardFactory = {
    BoardFactory(
      0 until tourRequest.board.rows map { rowIndex =>
        RowFactory(
          rowIndex, 0 until tourRequest.board.columns map { colIndex =>
            SquareFactory(rowIndex, colIndex)
          })
      })
  }
}
